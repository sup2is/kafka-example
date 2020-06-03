package me.sup2is.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GCMPushEntity {

    private String gcmToken;
    private String message;

}
