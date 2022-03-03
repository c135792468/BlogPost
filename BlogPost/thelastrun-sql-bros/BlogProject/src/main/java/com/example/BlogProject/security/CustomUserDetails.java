/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.security;

import com.example.BlogProject.models.Author;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Creating Custom User Details
 *
 * @author Gage
 */
public class CustomUserDetails implements UserDetails {
    String ROLE_PREFIX = "ROLE_";
    private Author user;
    private String role;
     
    public CustomUserDetails(Author user) {
        this.user = user;
        if(!this.user.isAuthorAdmin()){
            this.role = "USER";
        }
        else{
            this.role = "ADMIN";
        }
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        
        return list;
    }
 
    @Override
    public String getPassword() {
        return user.getAuthorPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getAuthorEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     
    public String getFullName() {
        return user.getAuthorName();
    }
    
}