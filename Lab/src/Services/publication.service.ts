import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Publication } from 'src/Models/Publication';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
  
  private readonly GATEWAY_URL = 'http://localhost:9000';
  private readonly PUBLICATION_API = `${this.GATEWAY_URL}/PUBLICATION-SERVICE/api/publications`;

  constructor(private httpClient: HttpClient) { }

  // // CRUD de base (avec auteurs avant)
  // getAllPublications(): Observable<Publication[]> {
  //   return this.httpClient.get<any>(this.PUBLICATION_API).pipe(
  //     map(response => {
  //       console.log('Réponse publications:', response);
  //       if (response._embedded && response._embedded.publications) {
  //         return response._embedded.publications;
  //       }
  //       if (Array.isArray(response)) {
  //         return response;
  //       }
  //       return [];
  //     })
  //   );
  // }

  //getAllPublications modifié
  getAllPublications(): Observable<Publication[]> {
  return this.httpClient.get<any>(this.PUBLICATION_API).pipe(
    map(response => {
      const pubs = response._embedded?.publications || response || [];
      return pubs.map((p: any) => ({
        id: p.id,
        titre: p.titre,
        type: p.type,
        dateApparition: p.date,  // map backend date → frontend dateApparition
        lien: p.lien,
        source: p.sourcePdf       // map backend sourcePdf → frontend source
      }));
    })
  );
}


  savePublication(publication: Publication): Observable<Publication> {
    return this.httpClient.post<Publication>(this.PUBLICATION_API, publication);
  }

  getPublicationById(id: number): Observable<Publication> {
    return this.httpClient.get<Publication>(`${this.PUBLICATION_API}/${id}`);
  }

  updatePublication(id: number, publication: Publication): Observable<Publication> {
    return this.httpClient.put<Publication>(`${this.PUBLICATION_API}/${id}`, publication);
  }

  deletePublication(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.PUBLICATION_API}/${id}`);
  }

  // Méthodes de recherche
  findByType(type: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByType?type=${type}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByTitreContaining(titre: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByTitreContaining?titre=${titre}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByDateApparitionBetween(startDate: string, endDate: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByDateApparitionBetween?start=${startDate}&end=${endDate}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByTypeAndYear(type: string, year: number): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByTypeAndYear?type=${type}&year=${year}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByTypeOrderByDateApparitionDesc(type: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByTypeOrderByDateApparitionDesc?type=${type}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  // Statistiques
  countByType(type: string): Observable<number> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/countByType?type=${type}`
    ).pipe(
      map(response => response || 0)
    );
  }
}