package grondag.renderbender;

import java.util.function.Consumer;
import java.util.function.Function;

public class FFTest implements Consumer<Function<String, Consumer<Boolean>>> {
	static FFTestImpl A;
	static FFTestImpl B;

	@Override
	public void accept(Function<String, Consumer<Boolean>> provider) {
		A = new FFTestImpl(provider.apply("ff_test_A"), 20);
		B = new FFTestImpl(provider.apply("ff_test_B"), 33);
	}
}
