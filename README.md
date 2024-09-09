# media-service
## Table of Contents

[1. About](#1-about)  
[2. Architecture](#2-architecture)  
  - [2.1 Ports](#21-ports)  
  - [2.2 Media Service Schema](#22-media-service-schema)  
  - [2.3 Endpoints](#23-endpoints)  
    - [2.3.1 Upload user profile picture](#231-upload-user-profile-picture)  
    - [2.3.2 Upload user banner picture](#232-upload-user-banner-picture)  
    - [2.3.3 Upload group profile picture](#231-upload-group-profile-picture)  
    - [2.3.4 Upload group banner picture](#232-upload-group-banner-picture)  
    - [2.3.5 Get image](#235-get-image)  

[3. Error Codes](#3-error-codes)  
[4. Notes](#4-notes)

<br />

## 1) About

The Media Service is a RESTful API for storage, retrieval, and management of media files like images and banners for the application.

<br />



## 2) Architecture
### 2.1) Ports

- **Service port:**  8086

- **Database port:**  5436

<br />


### 2.2) Media Service Schema

The database schema includes the following tables:


```sql
 CREATE TABLE images (
        id      SERIAL PRIMARY KEY,
        name    VARCHAR(255),
        type    VARCHAR(255),
        data    BYTEA
    );
```

<br />

### 2.3) Endpoints

**BASE URL:** `/api/media`.

All calls to this service must start with the base URL. Any additional URL fields will be specified
if relevant.

---

#### 2.3.1) Upload user profile picture

- **Method:**  POST

- **Endpoint:**  `/user/upload-profile-picture`

- **Description:**  Upload a user's profile picture to the service.

- **Query Parameters:**
  - `file` (MultipartFile) – The profile picture.
  - ```json
    {
      "ownerUserId": "uuid"
    }
    ```

- **Response:**

```json
{
    "message": "string",
    "imageUrl": "string",
    "imageName": "string",
    "type": "string"
}
```

- **Response HTTP Status:**
  - `200 OK` – Picture successfully uploaded.

---

#### 2.3.2) Upload user banner picture

- **Method:**  POST

- **Endpoint:**  `/user/upload-banner-picture`

- **Description:**  Upload a user's banner picture to the service.

- **Query Parameters:**
  - `file` (MultipartFile) – The banner picture.
  - ```json
    {
      "ownerUserId": "uuid"
    }
    ```

- **Response:**

```json
{
    "message": "string",
    "imageUrl": "string",
    "imageName": "string",
    "type": "string"
}
```

- **Response HTTP Status:**
  - `200 OK` – Picture successfully uploaded.

---


#### 2.3.3) Upload group profile picture

- **Method:**  POST

- **Endpoint:**  `/group/upload-profile-picture`

- **Description:**  Upload a group's profile picture to the service.

- **Query Parameters:**
  - `file` (MultipartFile) – The profile picture.
  - ```json
    {
      "ownerUserId": "uuid"
    }
    ```

- **Response:**

```json
{
    "message": "string",
    "imageUrl": "string",
    "imageName": "string",
    "type": "string"
}
```

- **Response HTTP Status:**
  - `200 OK` – Picture successfully uploaded.

---

#### 2.3.4) Upload group banner picture

- **Method:**  POST

- **Endpoint:**  `/group/upload-banner-picture`

- **Description:**  Upload a group's banner picture to the service.

- **Query Parameters:**
  - `file` (MultipartFile) – The banner picture.
  - ```json
    {
      "ownerUserId": "uuid"
    }
    ```

- **Response:**

```json
{
    "message": "string",
    "imageUrl": "string",
    "imageName": "string",
    "type": "string"
}
```

- **Response HTTP Status:**
  - `200 OK` – Picture successfully uploaded.

---

#### 2.3.4) Get image

- **Method:**  GET

- **Endpoint:**  `/id`

- **Description:**  Retrieves the image by its ID.

- **Path Variable:**
  - `id` (Long) – The image's ID.

- **Response:**
  - `ret` (byte[]) – A byte array of the retrieved image.


- **Response HTTP Status:**
  - `200 OK` – Image successfully retrieved.

  - `404 Not Found` – Image with the specified ID not found.


<br />


## 3) Error Codes

- `400 Bad Request` – The request could not be understood or was missing required parameters.

- `404 Not Found` – The specified resource could not be found.

- `500 Internal Server Error` – An error occurred on the server.

<br />



## 4) Notes

- Make sure to include the `Content-Type: application/json` header in your requests.

- Use valid UUIDs in your requests.
