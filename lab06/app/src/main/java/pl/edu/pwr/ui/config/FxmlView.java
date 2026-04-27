package pl.edu.pwr.ui.config;

public enum FxmlView {

    MAIN {

        @Override
        public String getFxmlPath() {
            return "/app-view.fxml";
        }
    },

     CLIENT_FORM {

        @Override
        public String getFxmlPath() {
            return "/client-form.fxml";
        }
    },

    OFFER_FORM {

        @Override
        public String getFxmlPath() {
            return "/offer-form.fxml";
        }
    },

    ACTIVITY_FORM {

        @Override
        public String getFxmlPath() {
            return "/activity-form.fxml";
        }
    },

    ORDER_FORM {

        @Override
        public String getFxmlPath() {
            return "/order-form.fxml";
        }
    };

    public abstract String getFxmlPath();
}