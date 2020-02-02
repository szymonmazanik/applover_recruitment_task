package pl.szymonmazanik.applover_recruitment_task.utils.extensions

import pl.szymonmazanik.applover_recruitment_task.model.entity.request.LoginRequest

fun LoginRequest.isInputDataValid() = email.isValidEmail() && password.isNotEmpty()