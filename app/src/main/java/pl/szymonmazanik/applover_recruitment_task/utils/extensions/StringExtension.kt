package pl.szymonmazanik.applover_recruitment_task.utils.extensions

fun String.isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()