create table if not exists websites (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL UNIQUE,
    site_name VARCHAR(100),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE if NOT EXISTS health_check (
    id SERIAL PRIMARY KEY,
    website_id integer references websites(id) ON DELETE CASCADE,
    website_status INTEGER,
    response_time_ms INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO websites (url, site_name) VALUES
('https://www.google.com', 'Google'),
('https://www.github.com', 'GitHub'),
('https://www.wikipedia.org', 'Wikipedia')
ON CONFLICT (url) DO NOTHING;