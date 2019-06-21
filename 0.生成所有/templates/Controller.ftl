package com.xakj.controller;

import com.xakj.service.*;
import com.xakj.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by hcmony on 2017/9/5.
 */
@Controller
public class ${modelName}Controller {
    
	@Autowired
	private ${modelName}Service ${modelName?uncap_first}Service;

	/**
     * 跳转列表
     */
    @RequestMapping(value = "/${modelName?uncap_first}/list", method = RequestMethod.GET)
    public String ${modelName?uncap_first}_list(ModelMap map) {

        return "${modelName?uncap_first}/${modelName}_list";
    }

    /**
     * 跳转新增
     */
    @RequestMapping(value = "/${modelName?uncap_first}/add", method = RequestMethod.GET)
    public String ${modelName?uncap_first}_add(ModelMap map) {

        return "${modelName?uncap_first}/${modelName}_add";
    }

    /**
     * 跳转更新
     */
    @RequestMapping(value = "/${modelName?uncap_first}/edit", method = RequestMethod.GET)
    public String ${modelName?uncap_first}_edit(ModelMap map,
            @RequestParam(value = "id") String id) {
        JSONObject object = (JSONObject)${modelName?uncap_first}Service.get(id);
        map.addAttribute("object", object);

        return "${modelName?uncap_first}/${modelName}_edit";
    }

    /**
     * 跳转详情
     */
    @RequestMapping(value = "/${modelName?uncap_first}/details", method = RequestMethod.GET)
    public String apply_details(ModelMap map,
            @RequestParam(value = "id") String id) {
        JSONObject object = (JSONObject)${modelName?uncap_first}Service.get(id);
        map.addAttribute("object", object);
        return "${modelName?uncap_first}/${modelName?uncap_first}_details";
    }
}
