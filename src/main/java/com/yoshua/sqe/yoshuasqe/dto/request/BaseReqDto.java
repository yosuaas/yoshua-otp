package com.yoshua.sqe.yoshuasqe.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseReqDto implements Serializable {
    private String userId;
}
