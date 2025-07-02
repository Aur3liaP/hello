-- Données de test pour H2
-- Départements
INSERT INTO departement (code, nom) VALUES ('33', 'Gironde');
INSERT INTO departement (code, nom) VALUES ('49', 'Maine-et-Loire');
INSERT INTO departement (code, nom) VALUES ('75', 'Paris');

-- Villes (ajustez les noms de colonnes selon votre schéma)
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Bordeaux', 250000, 1);
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Angers', 150000, 2);
INSERT INTO ville (nom, nb_habitants, departement_id) VALUES ('Paris', 2200000, 3);