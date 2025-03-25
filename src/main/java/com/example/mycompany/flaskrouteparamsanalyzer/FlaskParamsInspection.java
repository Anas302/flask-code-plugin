package com.example.mycompany.flaskrouteparamsanalyzer;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.python.psi.PyElementVisitor;
import com.jetbrains.python.psi.PyFunction;
import org.jetbrains.annotations.NotNull;


public class FlaskParamsInspection extends LocalInspectionTool {
    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new PyElementVisitor(){
            @Override
            public void visitPyFunction(@NotNull PyFunction function){
                // logic
                System.out.println("Visited a function");
                holder.registerProblem(function,
                        "Function args don't match route parameters",
                        new FlaskParamsQuickFix(function.getParameterList(), null));
            }
        };
    }
}
