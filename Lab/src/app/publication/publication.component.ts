import { Component } from '@angular/core';
import { Publication } from 'src/Models/Publication';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PublicationService } from 'src/Services/publication.service';
@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.css']
})
export class PublicationComponent {

  publications: Publication[] = [];
  filteredPublications: Publication[] = [];
  selectedPublication: Publication | null = null;
  isEditMode = false;
  isLoading = false;
  errorMessage = '';
  
  // Formulaires
  searchForm: FormGroup;
  filterForm: FormGroup;
  publicationForm: FormGroup;
  
  // Filtres
  publicationTypes = [
    'Article de journal',
    'Conférence',
    'Chapitre de livre',
    'Livre',
    'Thèse',
    'Rapport technique',
    'Prépublication'
  ];
  
  currentYear = new Date().getFullYear();
  years: number[] = [];
  
  // Pagination
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private publicationService: PublicationService,
    private fb: FormBuilder
  ) {
    // Générer les 5 dernières années
    for (let i = 0; i < 5; i++) {
      this.years.push(this.currentYear - i);
    }
    
    this.searchForm = this.fb.group({
      searchTerm: ['']
    });
    
    this.filterForm = this.fb.group({
      type: [''],
      year: [''],
      startDate: [''],
      endDate: ['']
    });
    
    this.publicationForm = this.fb.group({
      titre: [''],
      type: [''],
      dateApparition: [''],
      lien: [''],
      auteurs: [''],
      source: ['']
    });
  }

  ngOnInit(): void {
    this.loadPublications();
  }

  loadPublications(): void {
    this.isLoading = true;
    this.publicationService.getAllPublications().subscribe({
      next: (data) => {
        this.publications = data;
        this.filteredPublications = [...data];
        this.totalItems = data.length;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur chargement publications:', error);
        this.errorMessage = 'Erreur lors du chargement des publications';
        this.isLoading = false;
      }
    });
  }

  onSearch(): void {
    const searchTerm = this.searchForm.get('searchTerm')?.value;
    
    if (!searchTerm.trim()) {
      this.filteredPublications = [...this.publications];
      return;
    }
    
    this.publicationService.findByTitreContaining(searchTerm).subscribe({
      next: (data) => {
        this.filteredPublications = data;
      },
      error: (error) => {
        console.error('Erreur recherche:', error);
        this.errorMessage = 'Erreur lors de la recherche';
      }
    });
  }

  onFilter(): void {
    const filters = this.filterForm.value;
    
    // Appliquer les filtres localement pour l'exemple
    this.filteredPublications = this.publications.filter(pub => {
      let match = true;
      
      if (filters.type && pub.type !== filters.type) {
        match = false;
      }
      
      if (filters.year) {
        const pubYear = new Date(pub.dateApparition).getFullYear();
        if (pubYear !== filters.year) {
          match = false;
        }
      }
      
      if (filters.startDate && filters.endDate) {
        const pubDate = new Date(pub.dateApparition);
        const startDate = new Date(filters.startDate);
        const endDate = new Date(filters.endDate);
        
        if (pubDate < startDate || pubDate > endDate) {
          match = false;
        }
      }
      
      return match;
    });
  }

  resetFilters(): void {
    this.filterForm.reset();
    this.filteredPublications = [...this.publications];
  }

  selectPublication(publication: Publication): void {
    this.selectedPublication = publication;
    this.publicationForm.patchValue(publication);
    this.isEditMode = true;
  }

  newPublication(): void {
    this.selectedPublication = null;
    this.publicationForm.reset();
    this.isEditMode = false;
  }

  savePublication(): void {
    if (this.publicationForm.invalid) {
      return;
    }
    
    const publicationData = this.publicationForm.value;
    
    if (this.isEditMode && this.selectedPublication?.id) {
      // Mise à jour
      this.publicationService.updatePublication(
        this.selectedPublication.id, 
        publicationData
      ).subscribe({
        next: () => {
          this.loadPublications();
          this.newPublication();
        },
        error: (error) => {
          console.error('Erreur mise à jour:', error);
          this.errorMessage = 'Erreur lors de la mise à jour';
        }
      });
    } else {
      // Création
      this.publicationService.savePublication(publicationData).subscribe({
        next: () => {
          this.loadPublications();
          this.newPublication();
        },
        error: (error) => {
          console.error('Erreur création:', error);
          this.errorMessage = 'Erreur lors de la création';
        }
      });
    }
  }

  deletePublication(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette publication ?')) {
      this.publicationService.deletePublication(id).subscribe({
        next: () => {
          this.loadPublications();
          if (this.selectedPublication?.id === id) {
            this.newPublication();
          }
        },
        error: (error) => {
          console.error('Erreur suppression:', error);
          this.errorMessage = 'Erreur lors de la suppression';
        }
      });
    }
  }

  // Pagination
  get paginatedPublications(): Publication[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredPublications.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredPublications.length / this.itemsPerPage);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  // Export des données
  exportToCSV(): void {
    // Implémentez l'export CSV ici
    console.log('Export CSV des publications');
  }

  // Statistiques
  showStatistics(): void {
    // Implémentez l'affichage des statistiques
    console.log('Afficher statistiques');
  }
}