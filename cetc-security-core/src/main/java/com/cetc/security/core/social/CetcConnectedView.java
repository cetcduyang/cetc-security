/**
 *
 */
package com.cetc.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *绑定成功的视图
 */

public class CetcConnectedView extends AbstractView {


    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {


        response.setContentType("text/html;charset=UTF-8");
        if(model.get("connection") ==null){
            response.getWriter().write("<h3>解除绑定成功</h3>");
        }else{
            response.getWriter().write("<h3>绑定成功</h3>");
        }

    }

}
