export interface Evt {
    id?: number;  // Long côté backend devient number côté TypeScript
    titre: string;
    date: string; // Format ISO: "yyyy-MM-dd"
    lieu: string;
}