package me.sup2is.producer;

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
