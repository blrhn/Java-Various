package pl.edu.pwr.persistence.enums;

public enum ActivityType {
    PAYMENT {
        @Override
        public String getActivityType() {
            return "Płatność";
        }
    },
    DELIVERY {
        @Override
        public String getActivityType() {
            return "Przesyłka";
        }
    },
    REMINDER {
        @Override
        public String getActivityType() {
            return "Przypomnienie";
        }
    },
    CANCELLATION {
        @Override
        public String getActivityType() {
            return "Rezygnacja";
        }
    };

    public abstract String getActivityType();
}
