package br.com.financeirojavaspring.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@lombok.AllArgsConstructor
public class Preconditions {

  private boolean condition;

  public static Preconditions checkTrue(boolean condition) {
    return new Preconditions(condition);
  }

  public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
    if (!condition) {
      throw exceptionSupplier.get();
    }
  }
}
