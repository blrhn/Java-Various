package pl.edu.pwr.persistence.enums;

public enum MealType {
    BREAKFAST {
        @Override
        public String getMealType() {
            return "Śniadanie";
        }
    },
    LUNCH {
        @Override
        public String getMealType() {
            return "Obiad";
        }
    },
    DINNER {
        @Override
        public String getMealType() {
            return "Kolacja";
        }
    };

    public abstract String getMealType();
}
