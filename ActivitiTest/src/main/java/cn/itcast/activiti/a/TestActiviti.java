package cn.itcast.activiti.a;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestActiviti {
	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void createProcessEngine() {
		/*
		 * 使用代码配置流程引擎
		 */
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		configuration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		configuration.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:XE");
		configuration.setJdbcUsername("gx1110");
		configuration.setJdbcPassword("gx1110");
		configuration.setDatabaseSchemaUpdate("true");
		ProcessEngine engine = configuration.buildProcessEngine();
		System.out.println("工作流引擎创建成功");
	}
	@Test
	public void createProcessEngine1() {
		/*
		 * 使用配置文件配置流程引擎
		 */
		ProcessEngineConfiguration configuration = 
				ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine engine = configuration.buildProcessEngine();
		System.out.println("使用配置文件工作流引擎创建成功");
	}
	@Test
	public void createProcessEngine2() {
		/*
		 * 使用默认名称的配置文件activiti.cfg.xml配置流程引擎
		 * 类路径下activiti.cfg.xml
		 */
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		System.out.println("使用默认配置文件工作流引擎创建成功");
	}
	@Test
	public void deploy() {
		/*
		 * 使用默认名称的配置文件activiti.cfg.xml配置流程引擎
		 * 类路径下activiti.cfg.xml
		 */
		//获得流程引擎对象
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//获得服务
		RepositoryService service = engine.getRepositoryService();
		//创建部署的构建器
		DeploymentBuilder builder = service.createDeployment();
		/*
		 * 添加资源
		 * 设置名称
		 * 设置类别
		 * 部署
		 */
		Deployment deploy = builder.addClasspathResource("diagrams/LeaveBill.bpmn")
		.addClasspathResource("diagrams/LeaveBill.png")
		.name("请假单流程")
		.category("办公流程")
		.deploy();
		
		System.out.println(deploy.getId());
//		System.out.println("使用默认配置文件工作流引擎创建成功");
	}
	@Test
	public void createImage() throws Exception {
		String deploymentId = "1";
		String imageName = null;
		RepositoryService repositoryService = engine.getRepositoryService();
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		if(names != null && names.size() > 0) {
			for (String name : names) {
				if(name.endsWith(".png")) {
					imageName = name;
					break;
				}
			}
		}
		InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
		File file = new File("D:/"+imageName);
		FileUtils.copyInputStreamToFile(in, file);
		System.out.println("创建成功");
	}
}
