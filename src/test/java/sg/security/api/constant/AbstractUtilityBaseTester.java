package sg.security.api.constant;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractUtilityBaseTester<T> {

  @SuppressWarnings("unchecked")
  @Test
  public void shouldTestPrivateConstructor() {
    final Constructor<T> constructor;
    Class<T> persistentClass = null;
    try {
      persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
          .getActualTypeArguments()[0];

      this.checkFinalClass(persistentClass);

      constructor = this.checkConstructor(persistentClass);

      this.checkMethodsAreStatic(persistentClass);
      this.checkFieldsAreStatic(persistentClass);
      this.checkInstanciation(constructor, persistentClass);
    } catch (final SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      assertEquals(
          "java.lang.UnsupportedOperationException",
          UnsupportedOperationException.class.getName(), e.getCause().getClass().getName());
    }
  }

  private void checkFinalClass(final Class<T> persistentClass) {
    assertTrue(Modifier.isFinal(persistentClass.getModifiers()),
        "The class " + persistentClass.getName() + " must be final");
  }

  private Constructor<T> checkConstructor(final Class<T> persistentClass) {
    final Constructor<T> constructor;
    constructor = (Constructor<T>) persistentClass.getDeclaredConstructors()[0];
    assertTrue(Modifier.isPrivate(constructor.getModifiers()),
        "The constructor of the class " + persistentClass.getName() + ", must be private");
    constructor.setAccessible(true);
    return constructor;
  }

  private void checkInstanciation(final Constructor<T> constructor, final Class<T> persistentClass)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    final T instance = constructor.newInstance();
    fail("No excepction is thrown when instanciating "
        + persistentClass.getName()
        + " a UnsupportedOperationException should be thrown");
  }

  private void checkMethodsAreStatic(final Class<T> persistentClass) {
    final Method[] methods = persistentClass.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      assertTrue(
          Modifier.isStatic(methods[i].getModifiers()),
          "Method "
              + methods[i].getName() + " of the class " + persistentClass.getName()
              + " is NOT STATIC. All methods must be static");
    }
  }

  private void checkFieldsAreStatic(final Class<T> persistentClass) {
    final Field[] fields = persistentClass.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      assertTrue(
          Modifier.isStatic(fields[i].getModifiers()),
          "Field "
              + fields[i].getName() + " of the class " + persistentClass.getName()
              + " is NOT STATIC. All methods must be static");
    }
  }

}
