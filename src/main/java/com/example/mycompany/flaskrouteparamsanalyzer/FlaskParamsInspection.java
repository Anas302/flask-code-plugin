package com.example.mycompany.flaskrouteparamsanalyzer;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.python.psi.*;
import com.jetbrains.rd.generator.nova.PredefinedType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FlaskParamsInspection extends LocalInspectionTool {
    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new PyElementVisitor(){
            @Override
            public void visitPyFunction(@NotNull PyFunction function){
                PyDecorator route = function.getDecoratorList().getDecorators()[0]; // change [0]
                if (route == null)
                    return;

                PyExpression route_url = route.getArgumentList().getArguments()[0];
                if (route_url == null) // e.g. app.route()
                    return;

                List<String> url_args = extractUrlParameters(route_url.getText());
                if (url_args.isEmpty()) // e.g. app.route('/url/has/no/parameter')
                    return;

                PyParameter[] function_args = function.getParameterList().getParameters();
                if (function_args.length == 0) // def view(): ...
                    return;

                if (function_args.length != url_args.size())
                    return;

                // check all arguments names in route match function parameters
                for (int i = 0; i < function_args.length; i++) {
                    String function_arg_name = function_args[i].getName();
                    String url_arg_name = url_args.get(i);
                    if (!function_arg_name.equals(url_arg_name))
                        holder.registerProblem(function,
                                "Function parameter " + function_arg_name + " don't match route parameter " + url_arg_name,
                                new FlaskParamsQuickFix(function_args[i], url_arg_name));
                }
            }
        };
    }

    private List<String> extractUrlParameters(String url){
        List<String> params = new ArrayList<>();
        String regex = "<(.*?)>"; // Matches content inside < >
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(url);

        while (matcher.find())
            params.add(matcher.group(1)); // Extract content inside < >

        return params;
    }

    @Override
    public @Nullable String getStaticDescription() {
        return "This inspection checks if route arguments names in Flask match the function parameters.";
    }


}
