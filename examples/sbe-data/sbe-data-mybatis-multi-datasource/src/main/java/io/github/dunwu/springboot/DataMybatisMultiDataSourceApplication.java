package io.github.dunwu.springboot;

import io.github.dunwu.springboot.entity.User;
import io.github.dunwu.springboot.mapper.Db1UserMapper;
import io.github.dunwu.springboot.mapper.Db2UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/10/12
 */
@SpringBootApplication
@MapperScan("io.github.dunwu.springboot.mapper")
public class DataMybatisMultiDataSourceApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DataMybatisMultiDataSourceApplication.class);

	private final Db1UserMapper db1UserMapper;

	private final Db2UserMapper db2UserMapper;

	public DataMybatisMultiDataSourceApplication(Db1UserMapper db1UserMapper, Db2UserMapper db2UserMapper) {
		this.db1UserMapper = db1UserMapper;
		this.db2UserMapper = db2UserMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(DataMybatisMultiDataSourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (db1UserMapper == null) {
			log.error("连接 DB1 数据源失败");
			return;
		} else {
			log.info("连接 DB1 数据源成功");
		}

		db1UserMapper.insert(new User("张三", 21, "南京", "xxx@163.com"));
		db1UserMapper.insert(new User("李四", 28, "上海", "xxx@163.com"));

		List<User> userList = db1UserMapper.selectList(null);
		log.info("======= 打印 user 表所有数据 =======");
		userList.forEach(user -> {
			log.info(user.toString());
		});

		if (db2UserMapper == null) {
			log.error("连接 DB2 数据源失败");
			return;
		} else {
			log.info("连接 DB2 数据源成功");
		}

		db2UserMapper.insert(new User("王五", 24, "北京", "xxx@163.com"));

		List<User> userList2 = db2UserMapper.selectList(null);
		log.info("======= 打印 user 表所有数据 =======");
		userList2.forEach(user -> {
			log.info(user.toString());
		});
	}

}