import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPublicationComponent } from './modal-publication.component';

describe('ModalPublicationComponent', () => {
  let component: ModalPublicationComponent;
  let fixture: ComponentFixture<ModalPublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalPublicationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalPublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
