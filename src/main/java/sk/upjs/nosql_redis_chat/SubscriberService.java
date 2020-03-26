package sk.upjs.nosql_redis_chat;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.application.Platform;

public class SubscriberService extends Service<Void> {

	private RedisConnection redisConnection;
	private ObservableList<String> spravy;

	public SubscriberService(RedisConnection redisConnection, ObservableList<String> spravy) {
		this.redisConnection = redisConnection;
		this.spravy = spravy;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				StringRedisSerializer serializer = new StringRedisSerializer();
				byte[] channel = serializer.serialize("upjs*");
				redisConnection.pSubscribe((message, pattern) -> {
					String text = serializer.deserialize(message.getBody());
					Platform.runLater(() -> spravy.add(text));
				}, channel);
				return null;
			}
		};
	}

}
