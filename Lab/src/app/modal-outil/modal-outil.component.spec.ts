import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalOutilComponent } from './modal-outil.component';

describe('ModalOutilComponent', () => {
  let component: ModalOutilComponent;
  let fixture: ComponentFixture<ModalOutilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalOutilComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalOutilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
