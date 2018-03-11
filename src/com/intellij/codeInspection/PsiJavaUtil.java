package com.intellij.codeInspection;


import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


public class PsiJavaUtil {

    public static boolean isMethod(
            @NotNull PsiMethodCallExpression expression,
            @NotNull Set<String> names,
            @NotNull String containingClassQName) {

        return hasName(expression, names) && containingClassQName.equals(getContainingClassName(expression));
    }

    public static boolean isMethod(
        @NotNull PsiMethodCallExpression expression,
        @NotNull String name,
        @NotNull String containingClassQName) {

        return hasName(expression, name) && containingClassQName.equals(getContainingClassName(expression));
    }

    public static boolean hasName(@NotNull PsiMethodCallExpression expression, @NotNull String name) {
        final PsiReferenceExpression methodExpression = expression.getMethodExpression();
        return name.equals(methodExpression.getReferenceName());
    }
    public static boolean hasName(@NotNull PsiMethodCallExpression expression, @NotNull Set<String> names) {
        final PsiReferenceExpression methodExpression = expression.getMethodExpression();
        return names.contains(methodExpression.getReferenceName());
    }

    public static String getContainingClassName(@NotNull PsiMethodCallExpression expression) {
        final PsiMethod method = expression.resolveMethod();
        if (method == null) {
            return null;
        }
        final PsiClass aClass = method.getContainingClass();
        return aClass != null ? aClass.getQualifiedName() : null;
    }

}
