import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

public class PaddingTest {

	@Test
	public void test() {
		
		final Integer amount = 12000;
		final String actual = StringUtils.rightPad(StringUtils.leftPad(amount.toString(), 10, "0"), 12, "0");
		
		assertThat(
			actual, 
			Matchers.equalTo("000001200000")
		);
	}

}
