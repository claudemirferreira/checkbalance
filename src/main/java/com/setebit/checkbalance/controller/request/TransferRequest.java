package com.setebit.checkbalance.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private Long sourceAccountNumber;
    private Long targetAccountNumber;
    private Double amount;

}
