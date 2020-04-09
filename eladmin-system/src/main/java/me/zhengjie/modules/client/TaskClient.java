package me.zhengjie.modules.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import me.zhengjie.config.FeignConfig;

@Component
@FeignClient(value = "vmake-provider",path = "/vmake/provider/task" , configuration = FeignConfig.class) //这里的name对应调用服务的spring.applicatoin.name
public interface TaskClient {

	
	@PostMapping(value = "/taskClose")
	public Boolean taskClose(@RequestParam("id") String id);
	
	@PostMapping(value = "/taskOpen")
	public Boolean taskOpen(@RequestParam("id") String id);
	
	@PostMapping(value = "/checkSuccess")
	public Boolean checkSuccess(@RequestParam("id") String id);
	
	@PostMapping(value = "/checkFailure")
	public Boolean checkFailure(@RequestParam("id") String id);
	
}
