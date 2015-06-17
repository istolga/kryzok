package com.kruzok.api.config;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

	@Value("${mongodb.name}")
	private String dbName;

	@Value("${mongodb.host}")
	private String host;

	@Value("${mongodb.port}")
	private Integer port;

	@Value("${mongodb.username}")
	private String userName;

	@Value("${mongodb.password}")
	private String password;

	@Override
	protected String getDatabaseName() {
		return this.dbName;
	}

	@Override
	public MongoClient mongo() throws Exception {
		return new MongoClient(this.host, this.port);
	}

	@Override
	public UserCredentials getUserCredentials() {
		return new UserCredentials(this.userName, this.password);
	}

	@Override
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		ServerAddress serverAddress = new ServerAddress(this.host, this.port);
		MongoClient mongoClient = null;
		if (StringUtils.isNotBlank(this.userName)
				&& StringUtils.isNotBlank(this.password)) {
			// Set credentials
			MongoCredential credential = MongoCredential.createCredential(
					this.userName, getDatabaseName(),
					this.password.toCharArray());
			mongoClient = new MongoClient(serverAddress,
					Arrays.asList(credential));
		} else {
			mongoClient = new MongoClient(serverAddress);
		}
		// Mongo DB Factory
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
				mongoClient, getDatabaseName());

		return simpleMongoDbFactory;
	}

	@Override
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

}