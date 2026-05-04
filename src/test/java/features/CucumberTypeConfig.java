package features;

import com.xpeho.spring_boot_java_random_user.presentation.dto.UserRequestDTO;
import io.cucumber.java.DataTableType;

import java.util.List;
import java.util.Map;

/**
 * Cucumber converters — transforment automatiquement les données
 * des fichiers .feature en objets Java typés.
 */
public class CucumberTypeConfig {

    /**
     * Représente une ligne de vérification field/expected dans une DataTable.
     */
    public record FieldAssertion(String field, String expected) {}

    /**
     * Convertit chaque ligne de la DataTable (2 colonnes sans en-tête)
     * en un objet FieldAssertion.
     */
    @DataTableType
    public FieldAssertion fieldAssertion(List<String> row) {
        if (row.size() < 2) {
            throw new IllegalArgumentException(
                "DataTable row must have at least 2 columns (field | expected), got %d: %s"
                    .formatted(row.size(), row)
            );
        }
        return new FieldAssertion(row.get(0).trim(), row.get(1).trim());
    }

    /**
     * Convertit une ligne de DataTable (Map<String,String>) en UserRequestDTO.
     * Utilisable dans les .feature avec des tables à en-têtes.
     */
    @DataTableType
    public UserRequestDTO userRequest(Map<String, String> row) {
        return new UserRequestDTO(
                row.get("gender"),
                row.get("firstname"),
                row.get("lastname"),
                row.get("civility"),
                row.get("email"),
                row.get("phone"),
                row.get("picture"),
                row.get("nat")
        );
    }

}
