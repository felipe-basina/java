package br.com.redis.sample.as.data.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
public class JsonConvert {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Convert @object to @String JSON
	 * 
	 * @param object
	 * @return @String JSON for @object
	 */
	public String convertFromObjectToString(Object object) {
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
		
		// Conversão necessária para que o JSON não possua escape
		JsonParser parser = new JsonParser();
		JsonElement json = parser.parse(gson.toJson(object));
		
		return new Gson().toJson(json);
	}
	
	/**
	 * Convert @json to specific type @clazz
	 * 
	 * @param clazz
	 * @param json
	 * @return @clazz for @json String
	 */
	public <T> T convertFromJsonToObject(Class<T> clazz, String json) {
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);

			return objectMapper.readValue(json, clazz);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new IllegalArgumentException(
					String.format("Cannot convert String to type %s. Error: %s", clazz, ex.getMessage()));
		}
	}
	
	
}
