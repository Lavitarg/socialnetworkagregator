package instagram.model;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class SubsFilter {
    private List<String> checkedItems;
}