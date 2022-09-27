package com.trader.api.Dto;

import javax.validation.constraints.NotNull;

public record LoginRequestDto(@NotNull(message = "Email required") String email, @NotNull(message = "Password required") String password ) {
}
