# Vagrantfile for setting up a staging environment with GUI and extended timeout

Vagrant.configure("2") do |config|
  
    # Define the base box to use
    config.vm.box = "ubuntu/bionic64"
  
    # Set up the VM with GUI enabled and extended timeout
    config.vm.provider "virtualbox" do |vb|
      vb.gui = true  # Enable GUI mode to view the VM's startup process
      vb.memory = "2048"
      vb.cpus = 2
    end
  
    # Increase the boot timeout to allow for slower startups
    config.vm.boot_timeout = 600  # 10 minutes
  
    # Use a static IP to avoid DHCP conflicts
    config.vm.network "private_network", ip: "192.168.56.101"
  
    # Forward ports (adjust as needed for your application)
    config.vm.network "forwarded_port", guest: 8080, host: 8081
  
    # Define the provisioning steps
    config.vm.provision "shell", inline: <<-SHELL
      # Update package lists
      sudo apt-get update
      
      # Install Java (required for your Spring Boot application)
      sudo apt-get install -y openjdk-17-jdk
      
      # Install Gradle
      sudo apt-get install -y gradle
      
      # Install PostgreSQL (if used in your project)
      sudo apt-get install -y postgresql postgresql-contrib
      
      # Set up PostgreSQL (example setup)
      sudo -u postgres psql -c "CREATE USER vagrant WITH PASSWORD 'vagrant';"
      sudo -u postgres psql -c "ALTER USER vagrant CREATEDB;"
      sudo -u postgres psql -c "CREATE DATABASE ticketsystem WITH OWNER vagrant;"
      
      # Install Git
      sudo apt-get install -y git
  
      # Navigate to the project directory
      cd /vagrant
      
      # Run the build
      gradle build
      
      # Run the application (adjust according to your project's start command)
      java -jar build/libs/TicketSystem-0.0.1-SNAPSHOT.jar &
      
      echo "Deployment completed!"
    SHELL
  
  end
  