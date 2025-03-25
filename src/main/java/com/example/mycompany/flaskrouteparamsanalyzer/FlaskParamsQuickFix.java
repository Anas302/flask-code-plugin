package com.example.mycompany.flaskrouteparamsanalyzer;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.jetbrains.python.psi.PyParameterList;
import org.jetbrains.annotations.NotNull;

public class FlaskParamsQuickFix implements LocalQuickFix {

    private final PyParameterList functionParams;
    private final PyParameterList routeParams;

    public FlaskParamsQuickFix(PyParameterList functionParams, PyParameterList routeParams){
        this.functionParams = functionParams;
        this.routeParams = routeParams;
    }

    @Override
    public @IntentionName @NotNull String getName() {
        return LocalQuickFix.super.getName();
    }

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
        return "";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {

    }
}
