package examples.mapper;

import de.braintags.io.vertx.pojomapper.annotation.Entity;
import de.braintags.io.vertx.pojomapper.annotation.field.Id;
import io.vertx.docgen.Source;

@Source(translate = false)
@Entity
public class MiniMapper {
  @Id
  public String id;
  public String name;
  public int number;

  public MiniMapper() {
  }

}
