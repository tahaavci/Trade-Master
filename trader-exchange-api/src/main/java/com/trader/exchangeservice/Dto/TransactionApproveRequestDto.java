package com.trader.exchangeservice.Dto;

import javax.validation.constraints.NotNull;

public record TransactionApproveRequestDto(@NotNull Long queueId,@NotNull Boolean isApproved ) {
}
