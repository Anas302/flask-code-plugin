package com.example.mycompany.flaskrouteparamsanalyzer;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.python.psi.*;
import org.jetbrains.annotations.NotNull;

public class FlaskParamsQuickFix implements LocalQuickFix {

    private final PyParameter functionParam;
    private final String routeParamName;

    public FlaskParamsQuickFix(PyParameter functionParam, String routeParamName){
        this.functionParam = functionParam;
        this.routeParamName = routeParamName;
    }

    @Override
    public @IntentionName @NotNull String getName() {
        return "Rename function parameter " + functionParam.getName() + " to " + routeParamName;
    }

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
        return "Flask route parameters fixes";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        // element with the problem
        PsiElement element = descriptor.getPsiElement(); // PyFunction
        PyFunction function = (PyFunction) element;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PyParameter[] function_params = function.getParameterList().getParameters();
            for (PyParameter param : function_params)
                if (param == functionParam) {
                    PyElementGenerator generator = PyElementGenerator.getInstance(project);
                    PyParameter newParam = generator.createParameter(routeParamName);
                    param.replace(newParam);
                }
        });
    }
}
