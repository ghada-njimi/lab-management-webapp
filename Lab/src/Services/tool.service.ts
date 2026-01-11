import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Outil } from 'src/Models/Outil'; 

@Injectable({
  providedIn: 'root'
})
export class ToolService {

  private readonly GATEWAY_URL = 'http://localhost:9000'; 
  private readonly OUTIL_API = `${this.GATEWAY_URL}/OUTIL-SERVICE/api/outils`; // chemin complet vers  OutilController

  constructor(private httpClient: HttpClient) { }

  // Récupérer tous les outils
  getAllOutils(): Observable<Outil[]> {
    return this.httpClient.get<Outil[]>(this.OUTIL_API);
  }

  // Ajouter un outil
  addOutil(outil: Outil): Observable<Outil> {
    return this.httpClient.post<Outil>(this.OUTIL_API, outil);
  }

  // Récupérer un outil par id
  getOutilById(id: number): Observable<Outil> {
    return this.httpClient.get<Outil>(`${this.OUTIL_API}/${id}`);
  }

  // Mettre à jour un outil
  updateOutil(id: number, outil: Outil): Observable<Outil> {
    return this.httpClient.put<Outil>(`${this.OUTIL_API}/${id}`, outil);
  }

  // Supprimer un outil
  deleteOutil(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.OUTIL_API}/${id}`);
  }

  // Méthodes de recherche (optionnel)
  findBySource(source: string): Observable<Outil[]> {
    return this.httpClient.get<Outil[]>(`${this.OUTIL_API}/search/findBySource?source=${source}`);
  }
}
