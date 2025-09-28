Rails.application.config.middleware.insert_before 0, Rack::Cors do
  allow do
    origins '*' // todo: 실서비스에서는 프론트주소 같이 특정 도메인만 허용해야 함.

    resource '*', //
      headers: :any,
      methods: [:get, :post, :put, :patch, :delete, :options, :head]
  end
end