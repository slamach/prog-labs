package client.controllers.tools;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ResourceBundle;

/**
 * Observable Resource Factory.
 */
public class ObservableResourceFactory {

    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    /**
     * @return Resources.
     */
    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    /**
     * @return Resource bundle.
     */
    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    /**
     * Set resources.
     *
     * @param resources Resources.
     */
    public final void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    /**
     * Binds strings.
     *
     * @param key Key for resource.
     * @return Binding string.
     */
    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }
}
