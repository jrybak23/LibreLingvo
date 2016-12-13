CREATE TABLE Role
(
    id INT(11) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE User
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    activationKey VARCHAR(36),
    autoPlayDuringLesson BIT(1) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BIT(1) NOT NULL,
    lessonPartsCount INT(11) NOT NULL,
    minutesBetweenLessonParts INT(11) NOT NULL,
    name VARCHAR(255),
    nonLocked BIT(1) NOT NULL,
    password VARCHAR(255),
    registrationDate DATETIME,
    resetKey VARCHAR(36),
    translationsInOneLesson INT(11) NOT NULL
);

CREATE TABLE UserRole
(
    userId BIGINT(20) NOT NULL,
    roleId INT(11) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (roleId, userId),
    CONSTRAINT FK9e1tcga17su515dcucrlj3vtj FOREIGN KEY (userId) REFERENCES User (id),
    CONSTRAINT FK63vxkaqx2q3q577qvuqhbtqs8 FOREIGN KEY (roleId) REFERENCES Role (id)
);

CREATE TABLE Lesson
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    completedPartsOfLesson INT(11) NOT NULL,
    maxPartsOfLesson INT(11) NOT NULL,
    waitUnitNextLessonPart DATETIME
);

CREATE TABLE Word
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    langCode VARCHAR(2),
    text VARCHAR(300)
);

CREATE TABLE Tag
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    name VARCHAR(20),
    position INT(11),
    user_id BIGINT(20),
    CONSTRAINT FKdu7i3b6b914u19llhkyhln2m8 FOREIGN KEY (user_id) REFERENCES User (id)
);

CREATE TABLE Translation
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    lastModificationDate DATETIME NOT NULL,
    learned BIT(1) NOT NULL,
    learnedDate DATETIME,
    note LONGTEXT,
    partOfSpeech VARCHAR(255) NOT NULL,
    views INT(11) NOT NULL,
    lesson_id BIGINT(20),
    resultWord_id BIGINT(20),
    sourceWord_id BIGINT(20),
    user_id BIGINT(20),
    CONSTRAINT FKd1y193lexiuuiumumhgi3qslj FOREIGN KEY (lesson_id) REFERENCES Lesson (id),
    CONSTRAINT FKeot4ue01o344vkqh01bxcrt7d FOREIGN KEY (resultWord_id) REFERENCES Word (id),
    CONSTRAINT FK3x3s4ex6aoq3e85bsfeb1wt2e FOREIGN KEY (sourceWord_id) REFERENCES Word (id),
    CONSTRAINT FKggxiv2027nfjm4jp0747inx1i FOREIGN KEY (user_id) REFERENCES User (id)
);

CREATE TABLE TranslationTag
(
    tagId BIGINT(20) NOT NULL,
    translationId BIGINT(20) NOT NULL,
    CONSTRAINT `PRIMARY1` PRIMARY KEY (tagId, translationId),
    CONSTRAINT FKefbd0ubkj1o0t1lrgsxe8xkd9 FOREIGN KEY (tagId) REFERENCES Tag (id),
    CONSTRAINT FK1n0jynjf0dvo8el1djkt4y5xl FOREIGN KEY (translationId) REFERENCES Translation (id)
);

CREATE INDEX name_index ON Role (name);
CREATE INDEX FKdu7i3b6b914u19llhkyhln2m8 ON Tag (user_id);
CREATE INDEX FK3x3s4ex6aoq3e85bsfeb1wt2e ON Translation (sourceWord_id);
CREATE INDEX FKd1y193lexiuuiumumhgi3qslj ON Translation (lesson_id);
CREATE INDEX FKeot4ue01o344vkqh01bxcrt7d ON Translation (resultWord_id);
CREATE INDEX FKggxiv2027nfjm4jp0747inx1i ON Translation (user_id);
CREATE INDEX FK1n0jynjf0dvo8el1djkt4y5xl ON TranslationTag (translationId);
CREATE UNIQUE INDEX UK_e6gkqunxajvyxl5uctpl2vl2p ON User (email);
CREATE INDEX email_index ON User (email);
CREATE INDEX FK9e1tcga17su515dcucrlj3vtj ON UserRole (userId);
CREATE INDEX lang_code_index ON Word (langCode);
CREATE INDEX text_index ON Word (text);
