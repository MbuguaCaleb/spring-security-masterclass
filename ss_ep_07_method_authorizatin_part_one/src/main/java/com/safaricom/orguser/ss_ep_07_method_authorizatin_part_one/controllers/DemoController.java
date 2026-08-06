package com.safaricom.orguser.ss_ep_07_method_authorizatin_part_one.controllers;


import com.safaricom.orguser.ss_ep_07_method_authorizatin_part_one.security.DemoForConditionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DemoController {

    private final DemoForConditionEvaluator demoForConditionEvaluator;

    //todo(Notes)
    /*Any Annotation in Spring that is an aspect needs to be enabled*/
    /*For preauthorize annotation to work, i need to have enabled,GlobalMethodSecurity*/

    //it is an aspect that is working behind the scenes
    //before i give access to this resource, the spring security user must have the authority, read
    //preAuthorize is an aspect running before my resource
    //wow it is a bit cleaner on the method it self in thr reading point of view
    //especially in services with many endpoints

    //Annotation Types*/

    /**
     *
     * (a)PreAuthorize--->the annotation we use hundred percent of time. Before resource access
     *
     * (b)PostAuthorize is called so because the rules will be applied after the Method is called
     *    //Mainly used when we want to restrict the access to some returned value
     *
     *  //restricts the access to the return value if the condition is not executed correctly
     *     //Mainly used when we want to restrict the access to some returned value
     *     //Demo 5 will only be retuned if return is not Demo 5
     *
     *
     * (c)PreFilter
     *     //Whenever we use preFilter, we must have as a Parameter an Array
     *      if we have more than one filter in the List, we use the Filter Target to Specify
     *      the List which PreFilter should act on
     *
     *      It filters Based on a condition the values that are sent to the Method
     *
     * (d)PostFilter
     * //@PostFilter
     *     //the Post Filter will filter the return value based on the auhtorization condition
     *    //the return type must be either a collection or an array.
     *
     *    the returned collection must be mutable,
     *
     *            return List.of("abcd", "wert", "qajkhk", "rhsbs"); (Wont Work)
     *            new ArrayList<String> () -->Works
     */


    @GetMapping("/demo1")
    @PreAuthorize("hasAuthority('read')") // hasAuthority() hasAnyAuthority() hasRole() hasAnyRole()
    public String demo() {
        return "Demo";
    }

    @GetMapping("/demo2")
    @PreAuthorize("hasAnyAuthority('write', 'read')")
    public String demo2() {
        return "Demo";
    }

    //todo(Notes)
    //Using preAuthorize we are able to gain access of our PathVariables
    //authentication object from security context.
    //after auth is successful an authentication object is stored in the security context.
    //this means we can apply different authorization rules based on the data in the request
    //Not Only on the path or the Http Method (Matcher) but on the Data on the request
    //if specific params sent we can have different rules

    //the below implies that my request will only be authenticated only if my pathVariable
    //matches the name of the authenticated user.
    @GetMapping("/demo3/{smth}")
    @PreAuthorize("""
            #something == authentication.name or
            hasAnyAuthority('write','read')
            """)
    public String demo3(@PathVariable("smth") String something) {
        var a = SecurityContextHolder.getContext().getAuthentication();

        return "Demo";
    }


    //to get the authentication in the application we can anywhere use the security context holder.
    //the below is another way of calling a bean method inside an annotation
    //the below is another way you can call a bean method from inside an annotation.
    @GetMapping("/demo4/{smth}")
    @PreAuthorize("@demoForConditionEvaluator.condition()")
    public String demo4(@PathVariable("smth") String something) {
        var a = SecurityContextHolder.getContext().getAuthentication();
        return "Demo 4";
    }


    //restricts the access to the return value if the condition is not executed correctly
    //Mainly used when we want to restrict the access to some returned value
    //Demo 5 will only be retuned if return is not Demo 5
    //Never use Post Authorize with methods that change data
    @GetMapping("/demo5")
    @PostAuthorize("returnObject != 'Demo 5'") // hasAuthority() hasAnyAuthority() hasRole() hasAnyRole()
    public String demo5() {
        System.out.println("):");
        return "Demo 5";
    }

    //PreFilter
    //Whenever we use preFilter, we must have as a Parameter an Array
    //Works with either Arrays or collections

    //if we have more than one filter in the List, we use the Filter Target to Specify the List which PreFilter should act on
    //The below will allow only the Values that contain character A
    //values that do not contain a will be removed before the method is called
    //instead of adding the authorization for the filter after the controller, it should be done via preFilter
    //we decouple the logic related to security
    //We should not couple authorization logic inside the method or Business Logic
    @GetMapping("/demo6")
    @PreFilter("filterObject.contains('a')")
    public String demo6(@RequestBody List<String> values) {
        System.out.println("values" + values);
        return "Demo 6";
    }


    //@PostFilter
    //the Post Filter will filter the return value based on the auhtorization condition
   //the return type must be either a collection or an array.

           // return List.of("abcd", "wert", "qajkhk", "rhsbs");


    @GetMapping("/demo7")
    @PostFilter("filterObject.contains('a')")
    public List<String> demo7() {
        //N/B List.if creates an immutable collection
        var list = new ArrayList<String>();
        list.add("abcd");
        list.add("wert");
        list.add("qajkhk");
        list.add("rhsbs");

       //return List.of("abcd", "wert", "qajkhk", "rhsbs"); //(immutable collection , wont work)
        return list;
    }

}
