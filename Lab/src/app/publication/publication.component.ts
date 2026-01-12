import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Publication } from 'src/Models/Publication';
import { PublicationService } from 'src/Services/publication.service';
import { ModalPublicationComponent } from '../modal-publication/modal-publication.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.css']
})
export class PublicationComponent implements AfterViewInit {
  dataSource!: MatTableDataSource<Publication>;
  displayedColumns: string[] = ['id', 'titre', 'type', 'dateApparition', 'source', 'actions'];
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private publicationService: PublicationService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource<Publication>([]);
    this.loadPublications();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadPublications() {
    this.isLoading = true;
    this.publicationService.getAllPublications().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des publications:", err);
        this.showMessage("Erreur lors du chargement des publications");
        this.isLoading = false;
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  open() {
    const dialogRef = this.dialog.open(ModalPublicationComponent, {
      width: '600px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((publicationRecupere: Publication) => {
      if (publicationRecupere) {
        this.publicationService.savePublication(publicationRecupere).subscribe({
          next: () => {
            this.showMessage("Publication créée avec succès");
            this.loadPublications();
          },
          error: (err) => {
            console.error("Erreur lors de la sauvegarde de la publication:", err);
            this.showMessage("Erreur lors de la création de la publication");
          }
        });
      }
    });
  }

  openEdit(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = id;
    dialogConfig.width = '600px';
    dialogConfig.disableClose = true;

    const dialogRef = this.dialog.open(ModalPublicationComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((resForm: Publication) => {
      if (resForm) {
        this.publicationService.updatePublication(id, resForm).subscribe({
          next: () => {
            this.showMessage("Publication modifiée avec succès");
            this.loadPublications();
          },
          error: (err) => {
            console.error("Erreur lors de la modification:", err);
            this.showMessage("Erreur lors de la modification");
          }
        });
      }
    });
  }

  delete(id: number) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette publication ?")) {
      this.publicationService.deletePublication(id).subscribe({
        next: () => {
          this.showMessage("Publication supprimée avec succès");
          this.loadPublications();
        },
        error: (err) => {
          console.error("Erreur lors de la suppression:", err);
          this.showMessage("Erreur lors de la suppression");
        }
      });
    }
  }

  private showMessage(message: string) {
    this.snackBar.open(message, 'Fermer', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }
}