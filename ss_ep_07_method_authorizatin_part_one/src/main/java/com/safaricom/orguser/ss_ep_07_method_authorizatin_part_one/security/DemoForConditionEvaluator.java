package com.safaricom.orguser.ss_ep_07_method_authorizatin_part_one.security;

import org.springframework.stereotype.Component;

@Component
public class DemoForConditionEvaluator {

    public boolean condition(){

        //complex authorization condition
        return true;
    }
}
