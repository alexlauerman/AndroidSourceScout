package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

// Rule 13
public class WorldWriteableInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.WorldWriteableInspection");

    private static final Set<String> FLAGS = new HashSet<>();
    static {
        FLAGS.add("MODE_WORLD_READABLE");
        FLAGS.add("MODE_WORLD_WRITEABLE");
    }

    private static final Set<String> NAMES = new HashSet<>();
    static {
        NAMES.add("openFileOutput");
        NAMES.add("openOrCreateDatabase");
    }

    private static final String CONTAINING_CLASS = "android.content.ContextWrapper";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java13.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAMES, CONTAINING_CLASS)) {
                    PsiExpression[] args = expression.getArgumentList().getExpressions();
                    if (args.length > 1 && hasReadableWritableFlag(args[1])) {
                        holder.registerProblem(expression, AndroidCodeScannerBundle.message("java13.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                    }
                }
            }

            private boolean hasReadableWritableFlag(PsiExpression flagExpression) {
                if (flagExpression instanceof PsiBinaryExpression) {
                    PsiBinaryExpression binary = (PsiBinaryExpression) flagExpression;
                    if (JavaTokenType.OR.equals(binary.getOperationTokenType())) {
                        return hasReadableWritableFlag(binary.getLOperand()) || hasReadableWritableFlag(binary.getROperand());
                    }
                }
                if (flagExpression instanceof PsiReferenceExpression) {
                    PsiReferenceExpression referenceExpression = (PsiReferenceExpression) flagExpression;
                    return FLAGS.contains(referenceExpression.getReferenceName());
                }
                return false;
            }
        };
    }
}