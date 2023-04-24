package com.shaw.utils.utils;

import com.shaw.utils.web.WebUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author shaw
 * @date 2019年11月19日
 */
@Controller
public class ControllerUtils implements InitializingBean {

	private static RequestMappingHandlerMapping HANDLER_MAPPING;
	private static List<PatternsRequestCondition> AJAX_PATTERNS_REQUEST_CONDITIONS;

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	public static boolean isAjax(HttpServletRequest request) {
		if (WebUtils.isAjaxRequest(request)) {
			return true;
		} else {
			List<PatternsRequestCondition> ajaxPatternsRequestConditions = getAjaxPatternsRequestConditions();
			if (CollectionUtils.isNotEmpty(ajaxPatternsRequestConditions)) {
				for (PatternsRequestCondition condition : ajaxPatternsRequestConditions) {
					if (condition.getMatchingCondition(request) != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static List<PatternsRequestCondition> getAjaxPatternsRequestConditions() {
		if (AJAX_PATTERNS_REQUEST_CONDITIONS == null) {
			synchronized (ControllerUtils.class) {
				if (AJAX_PATTERNS_REQUEST_CONDITIONS == null) {
					AJAX_PATTERNS_REQUEST_CONDITIONS = Lists.newArrayList();
					RequestMappingHandlerMapping handlerMapping = getRequestMappingHandlerMapping();
					if (handlerMapping != null) {
						Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
						for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
							PatternsRequestCondition patternsRequestCondition = entry.getKey().getPatternsCondition();
							if (patternsRequestCondition != null) {
								RestController restController = AnnotationUtils
										.findAnnotation(entry.getValue().getBeanType(), RestController.class);
								if (restController != null) {
									AJAX_PATTERNS_REQUEST_CONDITIONS.add(patternsRequestCondition);
									continue;
								}

								ResponseBody responseBody = entry.getValue().getMethodAnnotation(ResponseBody.class);
								if (responseBody != null) {
									AJAX_PATTERNS_REQUEST_CONDITIONS.add(patternsRequestCondition);
									continue;
								}
							}
						}
					}
				}
			}
		}
		return AJAX_PATTERNS_REQUEST_CONDITIONS;
	}

	public static RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return HANDLER_MAPPING;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		HANDLER_MAPPING = requestMappingHandlerMapping;
	}

}
