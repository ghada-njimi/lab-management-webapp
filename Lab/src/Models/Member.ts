export interface Member {
  id?: number;
  cin: string;
  nom: string;
  prenom: string;
  date?: Date;
  photo?: string;
  cv?: string;
  email: string;
  password?: string;
  pubs?: Publication[];
  type?: string; // ← AJOUTER CE CHAMP
}

export interface Etudiant extends Member {
  type: 'etd';
  dateInscription?: Date;
  diplome: string;
  encadrant?: EnseignantChercheur;
}

export interface EnseignantChercheur extends Member {
  type: 'ens';
  grade: string;
  etablissement: string;
}
// Interface pour Publication
export interface Publication {
  id?: number;
  titre: string;
  type: string;
  dateApparition: Date;
  lien: string;
  sourcePdf?: string;
}