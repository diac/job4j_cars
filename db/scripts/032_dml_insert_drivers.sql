INSERT INTO
    driver(name, user_id)
VALUES
    (
		'Ivanov',
		(SELECT id FROM auto_user WHERE login = 'Ivanov')
	),
	(
		'Petrov',
		(SELECT id FROM auto_user WHERE login = 'Petrov')
	),
	(
		'Sidorov',
		(SELECT id FROM auto_user WHERE login = 'Sidorov')
	);