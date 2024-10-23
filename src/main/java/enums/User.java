package enums;

public enum User {
    VET_CHECHOV("vet_chehov"),
    VET_BUNIN("vet_bunin"),
    SUPER_ADMIN("superadmin"),
    EPIZ("epiz"),
    ADMIN_ORG("admin_org"),
    KAMERER("vet_kamerer"),

    // production
    VET_KOVEV_PROD("vet_konev"),
    SUPER_PROD("superadmin_prod"),
    EPIZ_PROD("epiz_prod"),
    ADMIN_ORG_PROD("admin_org_prod");
    private final String role;

    User(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    public static User getUserByRole(String role) {
        for (User user : values()) {
            if (user.getRole().equals(role)) {
                return user;
            }
        }
        return null;
    }
}