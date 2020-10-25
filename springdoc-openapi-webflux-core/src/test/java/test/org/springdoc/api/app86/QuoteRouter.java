/*
 *
 *  *
 *  *  * Copyright 2019-2020 the original author or authors.
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package test.org.springdoc.api.app86;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springdoc.core.fn.builders.ApiResponseBuilder;
import org.springdoc.core.fn.builders.OperationBuilder;
import org.springdoc.core.fn.builders.ParameterBuilder;
import org.springdoc.core.fn.builders.RequestBodyBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.core.Constants.OPERATION_ATTRIBUTE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuoteRouter {

	@Bean
	public RouterFunction<ServerResponse> myroute(QuoteHandler quoteHandler) {
		return route(GET("/hello").and(accept(TEXT_PLAIN)), quoteHandler::hello)
				.withAttribute(OPERATION_ATTRIBUTE, OperationBuilder.builder().operationId("hello").response(ApiResponseBuilder.builder().responseCode("200")).build())

				.and(route(POST("/echo").and(accept(TEXT_PLAIN).and(contentType(TEXT_PLAIN))), quoteHandler::echo)
						.withAttribute(OPERATION_ATTRIBUTE, OperationBuilder.builder().operationId("echo")
								.requestBody(RequestBodyBuilder.builder().implementation(String.class))
								.response(ApiResponseBuilder.builder().responseCode("200").implementation(String.class)).build()))

				.and(route(POST("/echo").and(accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))), quoteHandler::echo)
						.withAttribute(OPERATION_ATTRIBUTE, OperationBuilder.builder().operationId("echo")
								.requestBody(RequestBodyBuilder.builder().implementation(String.class))
								.response(ApiResponseBuilder.builder().responseCode("200").implementation(String.class)).build())
				)

				.and(route(GET("/quotes").and(accept(APPLICATION_JSON)), quoteHandler::fetchQuotes)
						.withAttribute(OPERATION_ATTRIBUTE, OperationBuilder.builder().operationId("fetchQuotes")
								.parameter(ParameterBuilder.builder().in(ParameterIn.QUERY).name("size").implementation(String.class))
								.response(ApiResponseBuilder.builder().responseCode("200").implementationArray(Quote.class)).build()))

				.and(route(GET("/quotes").and(accept(APPLICATION_STREAM_JSON)), quoteHandler::streamQuotes)
						.withAttribute(OPERATION_ATTRIBUTE, OperationBuilder.builder().operationId("fetchQuotes")
								.response(ApiResponseBuilder.builder().responseCode("200").implementation(Quote.class)).build()));
	}

}