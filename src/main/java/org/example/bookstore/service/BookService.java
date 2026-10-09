package org.example.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dao.AuthorDao;
import org.example.bookstore.dao.BookDao;
import org.example.bookstore.dao.CategoryDao;
import org.example.bookstore.dto.AuthorDto;
import org.example.bookstore.dto.BookDto;
import org.example.bookstore.dto.CategoryDto;
import org.example.bookstore.entity.Author;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookDao bookDao;
    private final CategoryDao categoryDao;
    private final AuthorDao authorDao;

    public BookDto findBookById(UUID id) {
        return bookDao.findBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<BookDto> listBooksByAuthor(String authorName){
        return bookDao.findBookByAuthorName(authorName);
    }

    public List<BookDto> listBooksByCategory(String categoryName){
        return bookDao.findBookByCategoryName(categoryName);
    }

    public Page<BookDto> findAllBooks(Pageable pageable) {
        return bookDao.listAllBooks(pageable);
    }

    public Page<BookDto> findBooksWithPagination(int pageZeroBased,int size){
        Pageable pageable= PageRequest.of(pageZeroBased,size,
                Sort.by("title").ascending());
        return bookDao.listAllBooks(pageable);
    }
    public List<AuthorDto> listAllAuthor(){
        return authorDao.findAll()
                .stream()
                .map(this::toAuthorDto)
                .toList();
    }
    public List<CategoryDto> listAllCategory(){
        return categoryDao.findAll()
                .stream()
                .map(this::toCategoryDto)
                .toList();
    }

    public CategoryDto toCategoryDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImage(),
                category.getSlug()
        );
    }


    public AuthorDto toAuthorDto(Author author){
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getBio(),
                author.getProfileImage(),
                author.getDateOfBith(),
                author.getNationality()
        );
    }


    public void initData() {
        // 1. Instantiating Categories
        Category classic = new Category("Classic", "Timeless literature that has stood the test of time.", "classic.jpg", "classic");
        Category history = new Category("History", "Books covering historical events, figures, and eras.", "history.jpg", "history");
        Category romance = new Category("Romance", "Stories centered around love, relationships, and emotional journeys.", "romance.jpg", "romance");
        Category biography = new Category("Biography", "Detailed accounts of real individuals written by another author.", "biography.jpg", "biography");
        Category detective = new Category("Detective", "Mystery and crime investigation novels involving detectives or sleuths.", "detective.jpg", "detective");
        Category fiction = new Category("Fiction", "Imaginative works of prose narrative not strictly based on real events.", "fiction.jpg", "fiction");

        List<Category> categories = List.of(classic, history, romance, biography, detective, fiction);
        categoryDao.saveAll(categories);

        Map<String, Category> catMap = categories.stream()
                .collect(Collectors.toMap(c -> c.getName().toLowerCase(), Function.identity()));

        // 2. Instantiating Authors
        List<Author> authors = List.of(
                new Author("Anthony Grey", "British journalist and author best known for Saigon and Peking.", "anthonygrey.jpg", LocalDate.of(1938, 7, 5), "British"),
                new Author("John Steinbeck", "American author and Nobel laureate known for The Grapes of Wrath.", "johnsteinbeck.jpg", LocalDate.of(1902, 2, 27), "American"),
                new Author("Yuval Noah Harari", "Israeli public intellectual and historian known for Sapiens.", "yuvalnoahharari.jpg", LocalDate.of(1976, 2, 24), "Israeli"),
                new Author("Nicholas Sparks", "American novelist and screenwriter who wrote iconic romance novels.", "nicholassparks.jpg", LocalDate.of(1965, 12, 31), "American"),
                new Author("Jeff Kinney", "American author and cartoonist famous for Diary of a Wimpy Kid.", "jeffkinney.jpg", LocalDate.of(1971, 2, 19), "American"),
                new Author("Guy Delisle", "Canadian cartoonist known for travelogue graphic novels.", "guydelisle.jpg", LocalDate.of(1966, 1, 19), "Canadian"),
                new Author("Aldous Huxley", "English writer and philosopher known for Brave New World.", "aldoushuxley.jpg", LocalDate.of(1894, 7, 26), "British"),
                new Author("Jack Kerouac", "American novelist and pioneer of the Beat Generation.", "jackkerouac.jpg", LocalDate.of(1922, 3, 12), "American"),
                new Author("Arthur Conan Doyle", "British author best remembered as creator of Sherlock Holmes.", "arthurconandoyle.jpg", LocalDate.of(1859, 5, 22), "British"),
                new Author("Toni Morrison", "American novelist and Nobel laureate celebrated for Beloved.", "tonimorrison.jpg", LocalDate.of(1931, 2, 18), "American"),
                new Author("Albert Camus", "French philosopher and author whose writings explored absurdism.", "albertcamus.jpg", LocalDate.of(1913, 11, 7), "French"),
                new Author("Franz Kafka", "Bohemian novelist famous for surreal themes like The Metamorphosis.", "franzkafka.jpg", LocalDate.of(1883, 7, 3), "Czech"),
                new Author("Jane Austen", "English novelist famous for Pride and Prejudice and Emma.", "janeausten.jpg", LocalDate.of(1775, 12, 16), "British"),
                new Author("Neil Gaiman", "English author of fantasy fiction, graphic novels, and Sandman.", "neilgaiman.jpg", LocalDate.of(1960, 11, 10), "British"),
                new Author("Anton Chekhov", "Russian master of modern short stories and classic plays.", "antonchekhov.jpg", LocalDate.of(1860, 1, 29), "Russian"),
                new Author("Mark Twain", "American humorist and author of Adventures of Huckleberry Finn.", "marktwain.jpg", LocalDate.of(1835, 11, 30), "American"),
                new Author("F. Scott Fitzgerald", "American novelist of the Jazz Age famous for The Great Gatsby.", "fscottfitzgerald.jpg", LocalDate.of(1896, 9, 24), "American"),
                new Author("Ernest Hemingway", "American author and Nobel laureate known for The Old Man and the Sea.", "ernesthemingway.jpg", LocalDate.of(1899, 7, 21), "American"),
                new Author("James Joyce", "Irish modernist novelist famous for Ulysses and Dubliners.", "jamesjoyce.jpg", LocalDate.of(1882, 2, 2), "Irish"),
                new Author("Virginia Woolf", "English pioneer of stream-of-consciousness in Mrs Dalloway.", "virginiawoolf.jpg", LocalDate.of(1882, 1, 25), "British"),
                new Author("H.P. Lovecraft", "American cosmic horror author creator of Cthulhu Mythos.", "hpLovecraft.jpg", LocalDate.of(1890, 8, 20), "American"),
                new Author("Gabriel García Márquez", "Colombian master of magical realism and One Hundred Years of Solitude.", "gabrielgarciamarquez.jpg", LocalDate.of(1927, 3, 6), "Colombian"),
                new Author("Agatha Christie", "English Queen of Crime, creator of Hercule Poirot and Miss Marple.", "agathachristie.jpg", LocalDate.of(1890, 9, 15), "British"),
                new Author("Haruki Murakami", "Japanese author of surrealistic novels like Norwegian Wood.", "harukimurakami.jpg", LocalDate.of(1949, 1, 12), "Japanese"),
                new Author("Leo Tolstoy", "Russian literary icon famous for War and Peace and Anna Karenina.", "leotolstoy.jpg", LocalDate.of(1828, 9, 9), "Russian"),
                new Author("George R.R. Martin", "American author famous for A Song of Ice and Fire series.", "georgerrmartin.jpg", LocalDate.of(1948, 9, 20), "American"),
                new Author("J.K. Rowling", "British author world-renowned for the Harry Potter series.", "jkrowling.jpg", LocalDate.of(1965, 7, 31), "British"),
                new Author("George Orwell", "English political satirist behind 1984 and Animal Farm.", "georgeorwell.jpg", LocalDate.of(1903, 6, 25), "British"),
                new Author("Stephen King", "American King of Horror with over 60 bestselling books.", "stephenking.jpg", LocalDate.of(1947, 9, 21), "American"),
                new Author("J.R.R. Tolkien", "English father of high fantasy, creator of The Lord of the Rings.", "jrrtolkien.jpg", LocalDate.of(1892, 1, 3), "British"),
                new Author("J.D. Salinger", "American novelist praised for The Catcher in the Rye.", "jdsalinger.jpg", LocalDate.of(1919, 1, 1), "American"),
                new Author("Fyodor Dostoevsky", "Russian psychological master behind Crime and Punishment.", "fyodordostoevsky.jpg", LocalDate.of(1821, 11, 11), "Russian")
        );
        authorDao.saveAll(authors);

        Map<String, Author> authorMap = authors.stream()
                .collect(Collectors.toMap(Author::getName, Function.identity()));

        // Helper list to collect created books
        List<Book> books = new ArrayList<>();

        // Helper Method for Book creation & linkage
        var createBook = new Object() {
            void add(String title, Integer isbn, String summary, double price, String img, int stock, Book.Status status, Category cat, Author auth) {
                Book b = new Book(null, title, isbn, summary, price, img, stock, status);
                auth.addBook(b);
                cat.addBook(b);
                books.add(b);
            }
        };

        // --- BOOK INSTANTIATIONS (306 items mapped to images) ---
        createBook.add("1984", 1000000001, "Dystopian classic exploring totalitarian control and surveillance.", 14.99, "1984.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George Orwell"));
        createBook.add("1Q84", 1000000002, "Surreal masterpiece blending parallel realities and mystery.", 18.50, "1q84.jpg", 12, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("21 Lessons for the 21st Century", 1000000003, "Profound examination of today's pressing technological issues.", 16.00, "21lessons.jpg", 18, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("The A.B.C. Murders", 1000000004, "Hercule Poirot confronts an alphabetical serial killer.", 11.99, "abcmurders.jpg", 8, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Across the River and Into the Trees", 1000000005, "An aging army colonel spends his final days in Venice.", 12.50, "acrosstheriver.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("The Adventures of Sherlock Holmes", 1000000006, "Collection of famous mystery cases solved by Sherlock Holmes.", 10.99, "adventuressherlock.jpg", 30, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("After Dark", 1000000007, "Atmospheric nocturnal tale set in Tokyo's hidden corners.", 13.00, "afterdark.jpg", 14, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("A Mercy", 1000000008, "Moving look at early American slavery and mothers and daughters.", 13.99, "amercy.jpg", 7, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("American Gods", 1000000009, "Old mythology fights modern concepts in contemporary America.", 15.99, "americangods.jpg", 20, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("Amerika", 1000000010, "Kafka's incomplete, surreal vision of an immigrant in America.", 9.99, "amerika.jpg", 5, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("A Murder is Announced", 1000000011, "Miss Marple unravels a murder invitation placed in a local paper.", 10.50, "amurderisannounced.jpg", 15, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Anansi Boys", 1000000012, "Witty fantasy involving trickster god Anansi's two sons.", 14.50, "anansiboys.jpg", 11, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("And Then There Were None", 1000000013, "Ten strangers trapped on an island are killed one by one.", 12.99, "andthentherewerenone.jpg", 40, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Animal Farm", 1000000014, "Satirical fable critiquing totalitarian revolution dynamics.", 8.99, "animalfarm.jpg", 50, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("George Orwell"));
        createBook.add("Anna Karenina", 1000000015, "Epic Russian masterpiece about passion, tragedy, and society.", 16.99, "annakarenina.jpg", 22, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Leo Tolstoy"));
        createBook.add("Antichay", 1000000016, "Reflective essays and letters written in intense historical moments.", 11.00, "antichay.jpg", 4, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Albert Camus"));
        createBook.add("Keep the Aspidistra Flying", 1000000017, "Satirical novel examining commercialism and artistic rebellion.", 10.99, "aspidistra.jpg", 6, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George Orwell"));
        createBook.add("At the Mountains of Madness", 1000000018, "Antarctic expedition discovers ancient cosmic horrors.", 11.50, "atthemountainsofmadness.jpg", 13, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("The Autumn of the Patriarch", 1000000019, "Magical realism portrait of a solitary Caribbean dictator.", 14.00, "autumnofthepatriarch.jpg", 9, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Gabriel García Márquez"));
        createBook.add("A Walk to Remember", 1000000020, "Heartwarming romance of unexpected transformation and devotion.", 10.99, "awalktoremember.jpg", 28, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("A Perfect Day for Bananafish", 1000000021, "Short story masterpiece examining psychological trauma.", 7.99, "a_perfect_day_for_bananafish.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("A Writer's Diary", 1000000022, "Introspective essays and journals of Russian literature giant.", 15.99, "a_writers_diary.jpg", 5, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("Babylon Revisited", 1000000023, "Short fiction exploring regret and the Paris Jazz Age hangover.", 8.99, "babylonrevisited.jpg", 12, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("The Beautiful and Damned", 1000000024, "Portrait of high society, privilege, and personal unraveling.", 11.99, "beautifulanddamned.jpg", 15, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Beloved", 1000000025, "Pulitzer-winning masterpiece on memory, family, and trauma.", 13.99, "beloved.jpg", 19, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("Big Sur", 1000000026, "Raw beat novel chronicling mental breakdown and natural isolation.", 12.00, "bigsur.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("A Boring Story", 1000000027, "Poignant Russian tale of an old professor assessing his life.", 7.50, "boringstory.jpg", 6, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("Brave New World", 1000000028, "Futuristic vision of a bio-engineered conditioning society.", 13.50, "bravenewworld.jpg", 35, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Aldous Huxley"));
        createBook.add("Brave New World Revisited", 1000000029, "Essays analyzing how close the modern world is to prophecy.", 11.99, "bravenewworldrevisited.jpg", 9, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Aldous Huxley"));
        createBook.add("Burma Chronicles", 1000000030, "Graphic novel diary detailing life in Myanmar as an expat.", 16.99, "burmachronicles.jpg", 17, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("Burmese Days", 1000000031, "Imperialistic criticism set in colonial British Burma.", 12.99, "burmesedays.jpg", 14, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("George Orwell"));
        createBook.add("Diary of a Wimpy Kid: Cabin Fever", 1000000032, "Greg Heffley gets snowed in with his family for holidays.", 9.99, "cabinfever.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jeff Kinney"));
        createBook.add("A Calendar of Wisdom", 1000000033, "Daily spiritual and philosophical insights compiled by Tolstoy.", 15.00, "calendarofwisdom.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Leo Tolstoy"));
        createBook.add("The Call of Cthulhu", 1000000034, "Foundational cosmic horror mythos tale of sleeping ancient entity.", 10.99, "callofcthulhu.jpg", 32, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("Camus at Combat", 1000000035, "Journalistic writings during French Resistance World War II.", 18.00, "camusatcombat.jpg", 5, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Albert Camus"));
        createBook.add("Camus: A Biography", 1000000036, "Detailed look into existential thinker's creative life.", 17.50, "camusbiography.jpg", 3, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Albert Camus"));
        createBook.add("Cannery Row", 1000000037, "Affectionate vignette of colorful characters in California.", 11.00, "canneryrow.jpg", 11, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("Carrie", 1000000038, "Iconic debut novel of telekinetic revenge at high school prom.", 12.99, "carrie.jpg", 21, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("The Case-Book of Sherlock Holmes", 1000000039, "Final anthology of short mystery investigations by Conan Doyle.", 10.50, "casebook.jpg", 12, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("The Casual Vacancy", 1000000040, "Darkly comic, tragic story of small town parish council election.", 14.99, "casualvacancy.jpg", 15, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("Harry Potter and the Chamber of Secrets", 1000000041, "Second year at Hogwarts uncovers ancient mysterious heir.", 12.99, "chamberofsecrets.jpg", 45, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("The Case of Charles Dexter Ward", 1000000042, "Chilling novel of occult resurrection and dark heritage.", 11.99, "charlesdexterward.jpg", 10, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("The Cherry Orchard", 1000000043, "Classic play detailing socio-economic shifts in aristocratic Russia.", 8.99, "cherryorchard.jpg", 14, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("Childhood", 1000000044, "Autobiographical novella tracing early formative memories.", 9.50, "childhood.jpg", 7, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Leo Tolstoy"));
        createBook.add("The Chinese Puzzle", 1000000045, "Gripping historical suspense set during turbulent events.", 13.99, "chinesepuzzle.jpg", 6, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Anthony Grey"));
        createBook.add("Chronicle of a Death Foretold", 1000000046, "Interrogative novella rebuilding unavoidable murder in small village.", 11.50, "chronicleofadeathforetold.jpg", 18, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Gabriel García Márquez"));
        createBook.add("A Clash of Kings", 1000000047, "Second epic installment in A Song of Ice and Fire.", 16.99, "clashofkings.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("Cognition", 1000000048, "Exploration of human thinking, bias, and processing history.", 17.00, "cognition.jpg", 8, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("The Cognitive Leap", 1000000049, "Analysis of how early humans developed complex speech.", 15.50, "cognitiveleap.jpg", 12, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("Colorless Tsukuru Tazaki", 1000000050, "Emotional quest of a man seeking why friends abandoned him.", 13.99, "colorless.jpg", 16, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("The Colour Out of Space", 1000000051, "Meteorite strikes New England farm causing eerie transformation.", 9.99, "colouroutofspace.jpg", 14, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("Coming Up for Air", 1000000052, "Nostalgic pre-WWII reflection on lost rural innocence.", 11.99, "comingupforair.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("George Orwell"));
        createBook.add("The Common Reader", 1000000053, "Insightful literary criticism essays on classic works.", 12.50, "commonreader.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));
        createBook.add("A Confession", 1000000054, "Personal spiritual crisis autobiographical work.", 10.00, "confession.jpg", 11, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Leo Tolstoy"));
        createBook.add("A Connecticut Yankee in King Arthur's Court", 1000000055, "Time-travel satire applying 19th-century tech to medieval times.", 10.99, "connecticutyankee.jpg", 13, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("Coraline", 1000000056, "Dark fantasy of a girl finding a sinister alternative home.", 11.99, "coraline.jpg", 30, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("The Cossacks", 1000000057, "Novella about a disillusioned nobleman entering Cossack culture.", 9.99, "cossacks.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Leo Tolstoy"));
        createBook.add("The Crack-Up", 1000000058, "Essays reflecting on personal loss, health, and artistic fame.", 12.00, "crackup.jpg", 6, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Crime and Punishment", 1000000059, "Psychological study of guilt and redemption in St. Petersburg.", 15.99, "crime_and_punishment.jpg", 35, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("Critical Writings", 1000000060, "Essays on aesthetics, Irish politics, and modern literature.", 14.50, "criticalwritings.jpg", 4, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("Crome Yellow", 1000000061, "Witty satire poking fun at intellectual party conversations.", 10.50, "cromeyellow.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Aldous Huxley"));
        createBook.add("The Cuckoo's Calling", 1000000062, "Private detective Cormoran Strike investigates a model's fall.", 14.99, "cuckooscalling.jpg", 22, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("J.K. Rowling"));
        createBook.add("Dagon", 1000000063, "Short horror piece detailing encounter with ancient sea deity.", 8.50, "dagon.jpg", 15, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("A Dance with Dragons", 1000000064, "Fifth epic volume of the Song of Ice and Fire fantasy saga.", 17.99, "dancewithdragons.jpg", 19, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("The Dark Tower: The Gunslinger", 1000000065, "Roland Deschain pursues the man in black across a desert.", 13.99, "darktower.jpg", 20, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("Dear John", 1000000066, "Moving love letters between a soldier and a college student.", 11.99, "dearjohn.jpg", 25, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("Death in the Afternoon", 1000000067, "Non-fiction study on history and passion of Spanish bullfighting.", 13.50, "deathintheafternoon.jpg", 8, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Ernest Hemingway"));
        createBook.add("Harry Potter and the Deathly Hallows", 1000000068, "Final battle against Voldemort and search for Horcruxes.", 16.99, "deathlyhallows.jpg", 50, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("Death of Mao", 1000000069, "Historical analysis of China following Mao Zedong's passing.", 15.00, "deathofmao.jpg", 5, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("Death on the Nile", 1000000070, "Poirot investigates murder onboard a luxury Egyptian cruise.", 12.99, "deathonnile.jpg", 30, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Demons", 1000000071, "Dark political satire on radical ideology in 19th century Russia.", 16.50, "demons.jpg", 11, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("The Devils of Loudun", 1000000072, "Historical account of alleged demonic possession in 1634 France.", 14.00, "devilsofloudun.jpg", 6, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Aldous Huxley"));
        createBook.add("The Dharma Bums", 1000000073, "Beat generation journey exploring Zen Buddhism and mountaineering.", 12.99, "dharmabums.jpg", 14, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("The Diamond as Big as the Ritz", 1000000074, "Satirical novella exploring extreme wealth and extravagance.", 8.99, "diamondasbigastheritz.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Diaries of Franz Kafka", 1000000075, "Personal journals detailing literary struggles and introspections.", 15.00, "diaries.jpg", 7, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Franz Kafka"));
        createBook.add("The Dilemma of a Ghost", 1000000076, "Dramatic exploration of cultural clashes in marriage.", 9.50, "dilemmaofghost.jpg", 4, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("The Diplomat's Wife", 1000000077, "Post-WWII suspense narrative of espionage and devotion.", 13.00, "diplomatswife.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Anthony Grey"));
        createBook.add("Doctor Sax", 1000000078, "Autobiographical mythic novel detailing childhood dreams.", 11.50, "doctorsax.jpg", 5, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("Doctor Sleep", 1000000079, "Sequel to The Shining following an adult Danny Torrance.", 14.99, "doctorsleep.jpg", 22, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("Diary of a Wimpy Kid: Dog Days", 1000000080, "Greg Heffley navigates summer vacation complications.", 9.99, "dogdays.jpg", 27, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jeff Kinney"));
        createBook.add("The Doors of Perception", 1000000081, "Philosophical exploration of mescaline and human perception.", 10.99, "doorsofperception.jpg", 18, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Aldous Huxley"));
        createBook.add("Down and Out in Paris and London", 1000000082, "Memoir of poverty and low-wage work in major cities.", 11.99, "downandout.jpg", 16, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("George Orwell"));
        createBook.add("Dream of Spring", 1000000083, "Historical narrative detailing political shifts in East Asia.", 14.50, "dreamofspring.jpg", 5, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("Dubliners", 1000000084, "Rich collection of 15 short stories depicting Dublin life.", 11.99, "dubliners.jpg", 25, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("The Duel", 1000000085, "Classic Russian novella focusing on honor and existential doubt.", 8.50, "duel.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("The Dunwich Horror", 1000000086, "Macabre tale of Wilbur Whateley and horrific monster in village.", 10.00, "dunwichhorror.jpg", 12, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("East of Eden", 1000000087, "Epic saga tracing intertwined destinies of two families.", 16.99, "eastofeden.jpg", 30, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("Emma", 1000000088, "Classic comedic romance about a well-meaning matchmaker.", 10.99, "emma.jpg", 28, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("Ethics and Society", 1000000089, "Moral philosophical reflections on historical progress.", 13.00, "ethics.jpg", 6, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("Evil Under the Sun", 1000000090, "Poirot investigates a glamourous actress murdered at resort.", 11.99, "evilunderthesun.jpg", 19, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Exile and the Kingdom", 1000000091, "Collection of short stories exploring isolation and solidarity.", 12.00, "exilekingdom.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Albert Camus"));
        createBook.add("Exiles", 1000000092, "James Joyce's only surviving play dealing with domestic strain.", 9.99, "exiles.jpg", 4, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("Eyeless in Gaza", 1000000093, "Non-linear novel charting a man's search for spiritual purpose.", 13.50, "eyelessingaza.jpg", 5, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Aldous Huxley"));
        createBook.add("Fantastic Beasts and Where to Find Them", 1000000094, "Guidebook to magical creatures within the wizarding universe.", 12.99, "fantasticbeasts.jpg", 35, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("A Farewell to Arms", 1000000095, "Tragic love story set against Italian campaign during WWI.", 13.99, "farewelltoarms.jpg", 24, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("A Feast for Crows", 1000000096, "Fourth volume in A Song of Ice and Fire dealing with war aftermath.", 16.99, "feastforcrows.jpg", 18, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("Finnegans Wake", 1000000097, "Complex, experimental modernist masterpiece.", 18.00, "finneganswake.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("Fire & Blood", 1000000098, "Definitive history of House Targaryen in Westeros.", 19.99, "fireandblood.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("Five Little Pigs", 1000000099, "Poirot re-investigates a sixteen-year-old painter murder case.", 11.50, "fivelittlepigs.jpg", 14, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Flappers and Philosophers", 1000000100, "Fitzgerald's first collection of short stories capturing 1920s.", 10.00, "flappersandphilosophers.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Flush: A Biography", 1000000101, "Imaginative biography of Elizabeth Barrett Browning's spaniel.", 9.99, "flush.jpg", 8, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Virginia Woolf"));
        createBook.add("For Whom the Bell Tolls", 1000000102, "Heroic epic of American dynamiter in Spanish Civil War.", 14.99, "forwhomthebelltolls.jpg", 26, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("Franny and Zooey", 1000000103, "Two novellas following spiritual breakdowns of Glass family.", 11.99, "franny_and_zooey.jpg", 17, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("Future Trends in Humanity", 1000000104, "Speculative essays on AI, biotechnology, and politics.", 16.50, "futuretrends.jpg", 11, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("A Game of Thrones", 1000000105, "First novel in epic A Song of Ice and Fire fantasy series.", 15.99, "gameofthrones.jpg", 45, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("The Garden of Eden", 1000000106, "Sensual, posthumously published novel set in French Riviera.", 12.99, "gardenofeden.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("The General in His Labyrinth", 1000000107, "Fictionalized final days of South American liberator Simón Bolívar.", 13.50, "generalinhislabyrinth.jpg", 10, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Gabriel García Márquez"));
        createBook.add("Giovanni's Room", 1000000108, "Moving story of love, identity, and isolation in Paris.", 12.50, "giovannisroom.jpg", 15, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Toni Morrison"));
        createBook.add("Harry Potter and the Goblet of Fire", 1000000109, "Harry unexpectedly competes in dangerous Triwizard Tournament.", 14.99, "gobletoffire.jpg", 40, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("God Help the Child", 1000000110, "Modern story exploring how childhood trauma shapes adults.", 13.00, "godhelpthechild.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("The Scripture of Golden Eternity", 1000000111, "Philosophical poem on Buddhist concepts and universal oneness.", 8.99, "goldeneternity.jpg", 6, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("Good Omens", 1000000112, "Witty apocalypse comedy co-written by Gaiman and Pratchett.", 14.99, "goodomens.jpg", 32, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("The Grapes of Wrath", 1000000113, "Dust Bowl saga of Joad family migrating to California.", 15.99, "grapesofwrath.jpg", 28, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("The Graveyard Book", 1000000114, "A boy raised by ghosts in a graveyard experiences adventures.", 12.50, "graveyardbook.jpg", 22, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("The Great Gatsby", 1000000115, "Classic Jazz Age portrait of ambition, obsession, and wealth.", 10.99, "greatgatsby.jpg", 60, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Hadji Murat", 1000000116, "Late masterpiece novella focusing on Chechen warrior allegiance.", 9.99, "hadjimurat.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Leo Tolstoy"));
        createBook.add("Harry Potter and the Half-Blood Prince", 1000000117, "Voldemort's past is uncovered while darkness spreads.", 15.99, "halfbloodprince.jpg", 38, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("A Happy Death", 1000000118, "Camus's precursor novel to The Stranger exploring happiness.", 11.00, "happydeath.jpg", 10, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Albert Camus"));
        createBook.add("Hapworth 16, 1924", 1000000119, "Long novella in letter format detailing Seymour Glass at camp.", 9.50, "hapworth_16_1924.jpg", 5, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("Hard-Boiled Wonderland and the End of the World", 1000000120, "Dual narrative splitting cyberpunk Tokyo and fantasy city.", 14.99, "hardboiledwonderland.jpg", 16, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("Hard Luck", 1000000121, "Introspective journey exploring grief and acceptance.", 10.50, "hardluck.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("The Haunter of the Dark", 1000000122, "Final story written in Cthulhu Mythos involving abandoned church.", 8.99, "haunterofthedark.jpg", 12, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("History of Tomorrow", 1000000123, "Exploration of human future, biotechnology, and dataism.", 16.00, "historyoftomorrow.jpg", 15, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("The History of Middle-earth", 1000000124, "Comprehensive 12-volume analysis of Middle-earth legendarium.", 25.00, "history_of_middle_earth.jpg", 10, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("Homage to Catalonia", 1000000125, "Personal account of fighting in Spanish Civil War.", 13.50, "homagetocatalonia.jpg", 14, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("George Orwell"));
        createBook.add("Homo Deus: A Brief History of Tomorrow", 1000000126, "Thought-provoking look at humanity's long-term future goals.", 17.50, "homodeus.jpg", 25, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("Hostage in Peking", 1000000127, "Journalist's memoir of two years under house arrest in China.", 14.99, "hostage.jpg", 7, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Anthony Grey"));
        createBook.add("The Hound of the Baskervilles", 1000000128, "Famous mystery concerning demonic hound on Devonshire moors.", 11.99, "houndbaskervilles.jpg", 35, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("Adventures of Huckleberry Finn", 1000000129, "Huck and escaped slave Jim raft down Mississippi River.", 10.99, "huckleberryfinn.jpg", 30, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("A Hunger Artist", 1000000130, "Short stories exploring artistic isolation and public decay.", 8.99, "hungerartist.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("In Dubious Battle", 1000000131, "Powerful novel about agricultural labor strike in California.", 12.50, "indubiousbattle.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("Innocent Eréndira", 1000000132, "Short stories showcasing surrealism and vibrant folklore.", 11.99, "innocenterendira.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Gabriel García Márquez"));
        createBook.add("The Innocents Abroad", 1000000133, "Humorous travelogue detailing American excursion through Europe.", 13.00, "innocentsabroad.jpg", 11, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Mark Twain"));
        createBook.add("In Our Time", 1000000134, "Hemingway's breakthrough collection of short vignettes.", 10.50, "inourtime.jpg", 13, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("Island", 1000000135, "Utopian counterpart novel to dystopian Brave New World.", 13.00, "island.jpg", 12, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Aldous Huxley"));
        createBook.add("Islands in the Stream", 1000000136, "Posthumously published novel about artist Thomas Hudson.", 12.99, "islandsinthestream.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("IT", 1000000137, "Seven friends confront ancient shape-shifting evil in Maine.", 18.99, "it.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("The Death of Ivan Ilyich", 1000000138, "Masterpiece novella on mortality and existential reflection.", 9.50, "ivanilyich.jpg", 18, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Leo Tolstoy"));
        createBook.add("Jacob's Room", 1000000139, "Experimental novel following life of Jacob Flanders.", 10.99, "jacobsroom.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));
        createBook.add("Jazz", 1000000140, "Passionate novel set during Harlem Renaissance.", 13.50, "jazz.jpg", 14, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("Jerusalem: Chronicles from the Holy City", 1000000141, "Graphic novel diary detailing life in Jerusalem.", 16.99, "jerusalem.jpg", 15, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("The Celebrated Jumping Frog", 1000000142, "Humorous short story that launched Mark Twain's career.", 7.99, "jumpingfrog.jpg", 12, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("Juvenilia", 1000000143, "Early writings, short plays, and verses of Jane Austen.", 9.99, "juvenilia.jpg", 5, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Jane Austen"));
        createBook.add("Kafka on the Shore", 1000000144, "Metaphysical journey of teenage runaway Kafka Tamura.", 15.99, "kafkaontheshore.jpg", 30, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("The Kingdom of God Is Within You", 1000000145, "Non-fiction philosophical treatise on Christian pacifism.", 14.00, "kingdomofgod.jpg", 8, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Leo Tolstoy"));
        createBook.add("Lady Susan", 1000000146, "Epistolary novella focused on scheming flirtatious widow.", 8.99, "ladysusan.jpg", 10, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("The Lady with the Dog", 1000000147, "Classic romance short story about unexpected love in Yalta.", 7.99, "ladywithdog.jpg", 15, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Anton Chekhov"));
        createBook.add("The Last Straw", 1000000148, "Suspenseful political novel set during international crisis.", 12.99, "laststraw.jpg", 6, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Anthony Grey"));
        createBook.add("The Last Tycoon", 1000000149, "Unfinished novel detailing Hollywood studio executive life.", 11.50, "lasttycoon.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Letters to Felice", 1000000150, "Collection of Kafka's passionate letters to Felice Bauer.", 16.00, "letterstofelice.jpg", 4, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Franz Kafka"));
        createBook.add("Life on the Mississippi", 1000000151, "Memoir of Mark Twain's days as steamboat pilot.", 12.99, "lifeonthemississippi.jpg", 10, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Mark Twain"));
        createBook.add("The Lion and the Unicorn", 1000000152, "Patriotic essays on British socialism and wartime strategy.", 10.00, "lionandunicorn.jpg", 6, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("George Orwell"));
        createBook.add("Lonesome Traveler", 1000000153, "Collection of personal travel essays across America and Mexico.", 11.99, "lonesometraveler.jpg", 9, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Jack Kerouac"));
        createBook.add("Diary of a Wimpy Kid: The Long Haul", 1000000154, "Heffley family road trip turns into unexpected disaster.", 9.99, "longhaul.jpg", 22, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jeff Kinney"));
        createBook.add("The Lost World", 1000000155, "Professor Challenger finds living dinosaurs on Amazon plateau.", 11.99, "lostworld.jpg", 18, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("Love and Other Stories", 1000000156, "Heartfelt short stories examining Russian human relationships.", 8.99, "love.jpg", 11, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Anton Chekhov"));
        createBook.add("Love in the Time of Cholera", 1000000157, "Enduring romance spanning fifty years of devotion.", 14.99, "loveinthetimeofcholera.jpg", 28, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Gabriel García Márquez"));
        createBook.add("Maggie Cassidy", 1000000158, "Autobiographical novel of teenage high school romance.", 10.99, "maggiecassidy.jpg", 7, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jack Kerouac"));
        createBook.add("The Man in a Case", 1000000159, "Short story about teacher isolated from human emotions.", 7.50, "maninacase.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("Mansfield Park", 1000000160, "Fanny Price navigates moral choices at country estate.", 11.99, "mansfieldpark.jpg", 20, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("Maps and Captives", 1000000161, "Historical narrative detailing border disputes and journeys.", 13.99, "maps.jpg", 5, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("Memoirs of James Joyce", 1000000162, "Biographical insights into modernist genius's writing.", 15.00, "memoirjoyce.jpg", 4, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("James Joyce"));
        createBook.add("Men Without Women", 1000000163, "Classic collection of short stories including The Killers.", 10.99, "menwithoutwomen.jpg", 12, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("The Metamorphosis", 1000000164, "Gregor Samsa wakes up transformed into a giant insect.", 9.99, "metamorphosis.jpg", 40, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("Misery", 1000000165, "Bestselling author held captive by obsessed fan.", 13.99, "misery.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("Money and Power", 1000000166, "Historical view on economic emergence across civilization.", 16.00, "money.jpg", 14, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("Montreal Graphic Novel", 1000000167, "Visual observation of urban life, family, and art.", 15.00, "montreal.jpg", 8, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("Montreal Diaries", 1000000168, "Autobiographical sketches during residence in Canada.", 14.50, "montrealdiaries.jpg", 6, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("Mrs Dalloway", 1000000169, "Stream-of-consciousness novel detailing day in post-WWI London.", 11.99, "mrsdalloway.jpg", 26, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));
        createBook.add("The Murder of Roger Ackroyd", 1000000170, "Poirot investigates mystery with famous plot twist.", 12.99, "murderofrogerackroyd.jpg", 30, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Murder on the Orient Express", 1000000171, "Poirot solves train murder where every suspect has a motive.", 12.99, "murderontheorientexpress.jpg", 45, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("My Lost City", 1000000172, "Nostalgic essays reflecting on Jazz Age New York.", 9.99, "mylostcity.jpg", 7, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("The Mysterious Affair at Styles", 1000000173, "Debut novel introducing Hercule Poirot to world of crime.", 10.99, "mysteriousaffairatstyles.jpg", 20, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("The Mysterious Stranger", 1000000174, "Satirical fable involving Satan visiting medieval Austrian village.", 10.50, "mysteriousstranger.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("The Myth of Sisyphus", 1000000175, "Philosophical essay defining absurdism through Greek myth.", 11.99, "mythsisyphus.jpg", 22, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Albert Camus"));
        createBook.add("Naked Earth", 1000000176, "Dramatic novel examining political turbulence in rural Asia.", 13.00, "nakedearth.jpg", 5, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Anthony Grey"));
        createBook.add("Neverwhere", 1000000177, "An ordinary man discovers magical subterranean London Below.", 13.99, "neverwhere.jpg", 24, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("Nights in Rodanthe", 1000000178, "Emotional story of two wounded souls meeting at coastal inn.", 11.50, "nightsinrodanthe.jpg", 18, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("Nine Stories", 1000000179, "Celebrated collection including For Esmé with Love and Squalor.", 11.99, "nine_stories.jpg", 20, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("Northanger Abbey", 1000000180, "Gothic novel parody centered on naive heroine Catherine Morland.", 10.99, "northangerabbey.jpg", 16, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("Norwegian Wood", 1000000181, "Nostalgic story of loss and sexuality in 1960s Tokyo.", 14.50, "norwegianwood.jpg", 35, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Haruki Murakami"));
        createBook.add("Notebooks of Albert Camus", 1000000182, "Personal journals detailing existential thoughts and drafts.", 14.00, "notebooks.jpg", 6, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Albert Camus"));
        createBook.add("Notes from Underground", 1000000183, "Existential novella written by bitter isolated St. Petersburg civil servant.", 10.99, "notes_from_underground.jpg", 28, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("No Writes for the Colonel", 1000000184, "Novella of an impoverished veteran awaiting promised pension.", 10.00, "nowritescol.jpg", 12, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Gabriel García Márquez"));
        createBook.add("The Ocean at the End of the Lane", 1000000185, "Man remembers childhood encounters with dark magical powers.", 13.50, "oceanattheend.jpg", 20, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("Of Love and Other Demons", 1000000186, "Tragic love story of young girl accused of rabies possession.", 12.50, "ofloveandotherdemons.jpg", 11, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Gabriel García Márquez"));
        createBook.add("Of Mice and Men", 1000000187, "Tragic tale of migrant workers George and Lennie.", 10.99, "ofmiceandman.jpg", 40, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("Of Mice and Men (Edition B)", 1000000188, "Classic story of friendship during American Great Depression.", 11.50, "ofmiceandmen.jpg", 15, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("The Old Man and the Sea", 1000000189, "Nobel-winning novella of Cuban fisherman Santiago's battle with marlin.", 9.99, "oldmanandthesea.jpg", 50, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("Old School", 1000000190, "Short stories exploring elite boarding school literary traditions.", 12.00, "oldschool.jpg", 7, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("One Hundred Years of Solitude", 1000000191, "Magical realism saga tracing Buendía family in Macondo.", 16.99, "onehundredyearsofsolitude.jpg", 40, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Gabriel García Márquez"));
        createBook.add("On the Road", 1000000192, "Define Beat Generation cross-country jazz trip novel.", 13.99, "ontheroad.jpg", 32, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("Harry Potter and the Order of the Phoenix", 1000000193, "Dumbledore's Army forms to fight Ministry denial and Voldemort.", 16.99, "orderofthephoenix.jpg", 35, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("Orlando: A Biography", 1000000194, "Satirical biography of poet changing sex across centuries.", 12.50, "orlando.jpg", 15, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));
        createBook.add("Paradise", 1000000195, "Explores history, religion, and violence in all-Black town.", 14.00, "paradise.jpg", 10, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("Peking", 1000000196, "Epic historical fiction drama during Chinese Cultural Revolution.", 15.00, "peking.jpg", 8, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("In the Penal Colony", 1000000197, "Horrific short story centered on elaborate execution machine.", 8.99, "penalcolony.jpg", 11, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("The Perennial Philosophy", 1000000198, "Comparative study of mysticism across human religions.", 14.99, "perennialphilosophy.jpg", 9, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Aldous Huxley"));
        createBook.add("Persuasion", 1000000199, "Anne Elliot reconnects with former fiancé Captain Wentworth.", 11.50, "persuasion.jpg", 22, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("Pet Sematary", 1000000200, "Terror unfolds when a family discovers ancient burial ground.", 13.99, "petsemetary.jpg", 27, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("Harry Potter and the Philosopher's Stone", 1000000201, "First novel discovering Hogwarts and wizard identity.", 12.99, "philosophersstone.jpg", 60, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("The Plague", 1000000202, "Existential novel detailing epidemic quarantine in Algerian town.", 13.99, "plague.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Albert Camus"));
        createBook.add("Point Counter Point", 1000000203, "Complex satire dissecting 1920s intellectual London society.", 13.50, "pointcounterpoint.JPG", 6, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Aldous Huxley"));
        createBook.add("The Poison Belt", 1000000204, "Professor Challenger series novel about poisonous space ether.", 9.99, "poisonbelt.jpg", 8, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("Polestar", 1000000205, "Suspense novel about Arctic expeditions and espionage.", 12.99, "polestar.jpg", 5, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Anthony Grey"));
        createBook.add("Poor Folk", 1000000206, "Dostoevsky's debut epistolary novel on poverty and dignity.", 9.50, "poor_folk.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("A Portrait of the Artist as a Young Man", 1000000207, "Stephen Dedalus's intellectual awakening in Dublin.", 11.99, "portraitofartist.jpg", 20, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("The Power of Knowledge", 1000000208, "Historical reflection on how information shapes empires.", 15.00, "powerofknowledge.jpg", 13, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("Pride and Prejudice", 1000000209, "Elizabeth Bennet and Mr. Darcy navigate love and pride.", 11.99, "prideandprejudice.jpg", 55, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("The Prince and the Pauper", 1000000210, "Two identical boys swap lives in Tudor England.", 10.50, "princeandpauper.jpg", 16, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("The Princess and the Queen", 1000000211, "Novella recounting Targaryen civil war Dance of the Dragons.", 11.99, "princessandqueen.jpg", 12, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("The Prisoner", 1000000212, "Dramatic suspense novel centered around political captivity.", 12.50, "prisoner.jpg", 7, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Anthony Grey"));
        createBook.add("Harry Potter and the Prisoner of Azkaban", 1000000213, "Sirius Black escapes Azkaban setting off search.", 13.99, "prisonerofazkaban.jpg", 42, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.K. Rowling"));
        createBook.add("Pudd'nhead Wilson", 1000000214, "Satirical mystery involving switched babies and fingerprinting.", 10.00, "puddnheadwilson.jpg", 9, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("Pyongyang: A Journey in North Korea", 1000000215, "Graphic novel diary detailing animation work in North Korea.", 15.99, "pyongyang.jpg", 20, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("Raise High the Roof Beam, Carpenters", 1000000216, "Two novellas focusing on Seymour Glass's wedding day.", 11.50, "raise_high_the_roof_beam.jpg", 14, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("The Red Lotus", 1000000217, "Historical fiction adventure set in East Asia.", 13.50, "redlotus.jpg", 6, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Anthony Grey"));
        createBook.add("Resurrection", 1000000218, "Tolstoy's final novel on legal injustice and moral redemption.", 14.50, "resurrection.jpg", 11, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Leo Tolstoy"));
        createBook.add("Return to Peking", 1000000219, "Follow-up travel memoir exploring modern China changes.", 14.00, "returntopeking.jpg", 5, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Anthony Grey"));
        createBook.add("The Road to Wigan Pier", 1000000220, "Sociological investigation of working-class life in England.", 12.50, "roadtowiganpier.jpg", 10, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("George Orwell"));
        createBook.add("Diary of a Wimpy Kid: Roderick Rules", 1000000221, "Greg Heffley deals with older brother Rodrick's antics.", 9.99, "rodickrules.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jeff Kinney"));
        createBook.add("A Room of One's Own", 1000000222, "Feminist essay on conditions required for women writers.", 10.99, "roomofonesown.jpg", 30, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Virginia Woolf"));
        createBook.add("Roughing It", 1000000223, "Humorous travelogue about Wild West silver mining fever.", 11.99, "roughingit.jpg", 8, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Mark Twain"));
        createBook.add("Safe Haven", 1000000224, "A mysterious woman arrives in small town seeking fresh start.", 11.99, "safehaven.jpg", 20, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("Saigon", 1000000225, "Epic historical novel set during French and American Vietnam conflicts.", 16.99, "saigon.jpg", 12, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("Sanditon", 1000000226, "Jane Austen's final, unfinished novel set at seaside resort.", 9.99, "sanditon.jpg", 14, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("The Sandman: Preludes & Nocturnes", 1000000227, "Dream, Lord of Stories, escapes imprisonment after decades.", 16.99, "sandmanpreludes.jpg", 28, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("Sapiens: A Brief History of Humankind", 1000000228, "Bestselling overview of human evolution and cognitive revolutions.", 18.99, "sapiens.jpg", 50, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Yuval Noah Harari"));
        createBook.add("Satori in Paris", 1000000229, "Autobiographical tale of short trip to France researching roots.", 10.50, "satoriparis.jpg", 8, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Jack Kerouac"));
        createBook.add("The Seagull", 1000000230, "Classic play exploring artistic ambition and romantic conflict.", 8.99, "seagull.jpg", 12, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("The Secret Adversary", 1000000231, "First Tommy and Tuppence detective adventure.", 10.99, "secretadversary.jpg", 16, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Agatha Christie"));
        createBook.add("Sense and Sensibility", 1000000232, "Dashwood sisters balance emotion and logic in romance.", 11.99, "senseandsensibility.jpg", 30, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("The Shadow Out of Time", 1000000233, "Mind-swapping alien entity takes control of professor.", 10.50, "shadowoutoftime.jpg", 11, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("The Shadow over Innsmouth", 1000000234, "Traveler discovers horror in decaying fish-hybrid coastal village.", 11.00, "shadowoverinnsmouth.jpg", 22, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("H.P. Lovecraft"));
        createBook.add("Shenzhen: A Travelogue in China", 1000000235, "Graphic novel observations on working animator life in China.", 15.50, "shenzhen.jpg", 14, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("The Sign of the Four", 1000000236, "Sherlock Holmes solves mystery involving Agra treasure theft.", 10.99, "signfour.jpg", 25, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("Silk Road", 1000000237, "Historical narrative following trade, culture, and conflict.", 14.50, "silkroad.jpg", 6, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("The Sleeper and the Spindle", 1000000238, "Dark fairytale retelling woven with rich illustrations.", 12.99, "sleeperandspindle.jpg", 15, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("Song of Solomon", 1000000239, "Milkman Dead discovers identity and ancestry.", 13.99, "songofsolomon.jpg", 18, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("South of the Border, West of the Sun", 1000000240, "Nostalgic love story of adult reconnecting with childhood friend.", 13.00, "southoftheborder.jpg", 21, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Haruki Murakami"));
        createBook.add("Sputnik Sweetheart", 1000000241, "Unrequited love story involving sudden disappearance on island.", 12.99, "sputniksweetheart.jpg", 19, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Haruki Murakami"));
        createBook.add("Stardust", 1000000242, "Young man ventures into Faerie realm to catch a fallen star.", 13.99, "stardust.jpg", 26, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Neil Gaiman"));
        createBook.add("Stephen Hero", 1000000243, "Early draft version of A Portrait of the Artist as a Young Man.", 10.50, "stephenhero.jpg", 4, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("A Storm of Swords", 1000000244, "Third epic installment featuring the infamous Red Wedding.", 16.99, "stormofswords.jpg", 30, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("George R.R. Martin"));
        createBook.add("The Story of a Shipwrecked Sailor", 1000000245, "Journalistic narrative of sailor surviving ten days on raft.", 10.99, "storyofshipwrecked.jpg", 12, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Gabriel García Márquez"));
        createBook.add("A Study in Scarlet", 1000000246, "First Sherlock Holmes novel introducing Dr. John Watson.", 10.99, "studyscarlet.jpg", 35, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Arthur Conan Doyle"));
        createBook.add("The Subterraneans", 1000000247, "Beat generation novel centered on romance in San Francisco.", 11.50, "subterraneans.jpg", 9, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jack Kerouac"));
        createBook.add("Such, Such Were the Joys", 1000000248, "Autobiographical essay detailing harsh prep school years.", 9.99, "suchwerethejoys.jpg", 7, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("George Orwell"));
        createBook.add("Sula", 1000000249, "Intense friendship between two Black women in Ohio.", 12.99, "sula.jpg", 16, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("Sweet Thursday", 1000000250, "Sequel to Cannery Row capturing postwar California life.", 11.99, "sweetthursday.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("Tales from the Perilous Realm", 1000000251, "Collection of Tolkien's short fantasy stories and poems.", 14.99, "tales_from_the_perilous_realm.jpg", 15, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("Tar Baby", 1000000252, "Complex love story set on Caribbean island.", 13.50, "tarbaby.jpg", 9, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("Tender Is the Night", 1000000253, "Tragic decline of psychoanalyst Dick Diver and wealthy wife.", 12.99, "tenderisthenight.jpg", 18, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("The Best of Me", 1000000254, "High school sweethearts reunite twenty years later at funeral.", 11.99, "thebestofme.jpg", 22, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("The Bet", 1000000255, "Short story about banker and lawyer making fifteen-year wager.", 7.99, "thebet.jpg", 14, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("The Bluest Eye", 1000000256, "Morrison's debut novel exploring race, beauty, and trauma.", 12.99, "thebluesteye.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Toni Morrison"));
        createBook.add("The Burrow", 1000000257, "Kafka's short story of mole-like creature obsessed with security.", 8.50, "theburrow.jpg", 6, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("The Castle", 1000000258, "Land surveyor K. struggles against mysterious bureaucracy.", 13.99, "thecastle.jpg", 17, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("The Choice", 1000000259, "Romance novel examining life-altering decisions and love.", 11.50, "thechoice.jpg", 20, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("The Dead", 1000000260, "Famous novella ending Dubliners, dealing with memory and love.", 8.99, "thedead.jpg", 12, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("James Joyce"));
        createBook.add("The Death of Mao", 1000000261, "Historical inquiry into political transitions in modern China.", 14.50, "thedeathofmao.jpg", 4, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("The Fall", 1000000262, "Philosophical monologue set in Amsterdam bar.", 10.99, "thefall.jpg", 13, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Albert Camus"));
        createBook.add("The Grapes of Wrath (Edition B)", 1000000263, "Hardcover classic detailing Great Depression Dust Bowl struggle.", 16.50, "thegrapesofwrath.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("The Hostage", 1000000264, "Thriller focused on diplomatic captivity during international conflict.", 12.99, "thehostage.jpg", 6, Book.Status.IN_STOCK, catMap.get("detective"), authorMap.get("Anthony Grey"));
        createBook.add("The Hostages", 1000000265, "Dramatic account of journalist captivity in East Asia.", 13.50, "thehostages.jpg", 5, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Anthony Grey"));
        createBook.add("The Institute", 1000000266, "Children with special talents trapped in sinister facility.", 15.99, "theinstitute.jpg", 28, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("The Judgement", 1000000267, "Kafka's breakthrough story of son's troubled relationship with father.", 7.99, "thejudgement.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("The Last Song", 1000000268, "Rebellious teen connects with father over summer through music.", 11.99, "thelastsong.jpg", 22, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("The Lucky One", 1000000269, "US Marine searches for woman in photograph that saved his life.", 11.99, "theluckyone.jpg", 24, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("The Moon Is Down", 1000000270, "Resilient resistance in small town occupied by army.", 11.50, "themoonisdown.jpg", 11, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("The Naked Earth", 1000000271, "Historical novel about land reform turmoil.", 13.50, "thenakedearth.jpg", 5, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Anthony Grey"));
        createBook.add("The Notebook", 1000000272, "Enduring romantic tale of Noah and Allie across decades.", 10.99, "thenotebook.jpg", 50, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Nicholas Sparks"));
        createBook.add("The Pearl", 1000000273, "Parable of fisherman Kino finding enormous valuable pearl.", 9.99, "thepearl.jpg", 35, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("The Rebel", 1000000274, "Philosophical treatise examining revolution and human freedom.", 13.99, "therebel.jpg", 10, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Albert Camus"));
        createBook.add("The Red Pony", 1000000275, "Vignettes following young boy Jody growing up on California ranch.", 9.50, "theredpony.jpg", 14, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("John Steinbeck"));
        createBook.add("The Shining", 1000000276, "Jack Torrance becomes winter caretaker at haunted Overlook Hotel.", 14.99, "theshining.jpg", 40, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("The Stand", 1000000277, "Post-apocalyptic vision of survivors caught in good vs evil war.", 18.99, "thestand.jpg", 20, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Stephen King"));
        createBook.add("The Stranger", 1000000278, "Meursault faces trial after detached murder on Algerian beach.", 11.99, "thestranger.jpg", 45, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Albert Camus"));
        createBook.add("The Sun Also Rises", 1000000279, "Lost Generation expatriates travel from Paris to Pamplona.", 12.99, "thesunalsorises.jpg", 30, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("The Trial", 1000000280, "Josef K. is arrested and prosecuted by remote, inaccessible authority.", 12.99, "thetrial.jpg", 32, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Franz Kafka"));
        createBook.add("The Watsons", 1000000281, "Unfinished novel following Emma Watson returning to family home.", 8.99, "thewatsons.jpg", 9, Book.Status.IN_STOCK, catMap.get("romance"), authorMap.get("Jane Austen"));
        createBook.add("The Waves", 1000000282, "Experimental stream-of-consciousness novel tracking six friends.", 12.50, "thewaves.jpg", 11, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));
        createBook.add("The Years", 1000000283, "Traces fifty years in life of prominent Pargiter family.", 13.00, "theyears.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));
        createBook.add("The Brothers Karamazov", 1000000284, "Theological and moral masterpiece focused on patricide.", 16.99, "the_brothers_karamazov.jpg", 25, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("The Catcher in the Rye", 1000000285, "Holden Caulfield wanders New York exploring adolescent isolation.", 11.99, "the_catcher_in_the_rye.jpg", 50, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("The Children of Húrin", 1000000286, "Tragic Middle-earth legend during First Age.", 14.99, "the_children_of_hurin.jpg", 18, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The Fellowship of the Ring", 1000000287, "First part of Lord of the Rings epic quest to destroy One Ring.", 15.99, "the_fellowship_of_the_ring.jpg", 45, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The Gambler", 1000000288, "Novella exploring gambling addiction and volatile relationships.", 10.50, "the_gambler.jpg", 13, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("The Hobbit", 1000000289, "Bilbo Baggins journeys to Lonely Mountain with dwarves.", 13.99, "the_hobbit.jpg", 60, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The House of the Dead", 1000000290, "Fictionalized memoir of Siberian prison camp exile experience.", 12.00, "the_house_of_the_dead.jpg", 10, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("The Idiot", 1000000291, "Tragic tale of saintly Prince Myshkin in cynical Russian society.", 14.99, "the_idiot.jpg", 20, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Fyodor Dostoevsky"));
        createBook.add("The Letters of J.R.R. Tolkien", 1000000292, "Collected correspondence shedding light on Middle-earth lore.", 16.50, "the_letters_of_jrr_tolkien.jpg", 8, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The Return of the King", 1000000293, "Final volume of Lord of the Rings trilogy.", 15.99, "the_return_of_the_king.jpg", 40, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The Sea and the Glass", 1000000294, "Suspenseful narrative set against coastal background.", 12.00, "the_sea_and_the_glass.jpg", 5, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Anthony Grey"));
        createBook.add("The Silmarillion", 1000000295, "Mythological creation history of Middle-earth prior to Lord of Rings.", 15.99, "the_silmarillion.jpg", 30, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The Two Towers", 1000000296, "Second volume of Lord of the Rings tracking split fellowship.", 15.99, "the_two_towers.jpg", 42, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("J.R.R. Tolkien"));
        createBook.add("The Uncollected Stories", 1000000297, "Collection of early unpublished short fiction.", 11.00, "the_uncollected_stories.jpg", 6, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("The Young Folks", 1000000298, "Debut short story published in 1940 examining youth culture.", 7.99, "the_young_folks.jpg", 8, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("J.D. Salinger"));
        createBook.add("Diary of a Wimpy Kid: The Third Wheel", 1000000299, "Greg Heffley scrambles to find a dance date for Valentine's Day.", 9.99, "thirdwheel.jpg", 25, Book.Status.IN_STOCK, catMap.get("fiction"), authorMap.get("Jeff Kinney"));
        createBook.add("This Side of Paradise", 1000000300, "Debut novel following post-WWI Princeton youth generation.", 10.99, "thissideofparadise.jpg", 15, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("F. Scott Fitzgerald"));
        createBook.add("Three Guineas", 1000000301, "Major essay discussing anti-war, fascism, and women's rights.", 11.50, "threeguineas.jpg", 9, Book.Status.IN_STOCK, catMap.get("history"), authorMap.get("Virginia Woolf"));
        createBook.add("Three Sisters", 1000000302, "Classic play detailing Prozorov sisters longing to return to Moscow.", 8.99, "threesisters.jpg", 14, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Anton Chekhov"));
        createBook.add("Tijuana Graphic Novel", 1000000303, "Graphic travelogue observing border dynamics.", 14.50, "tijuana.jpg", 7, Book.Status.IN_STOCK, catMap.get("biography"), authorMap.get("Guy Delisle"));
        createBook.add("To Have and Have Not", 1000000304, "Harry Morgan turns to contraband running between Cuba and Florida.", 11.99, "tohaveandhavenot.jpg", 12, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Ernest Hemingway"));
        createBook.add("The Adventures of Tom Sawyer", 1000000305, "Young boy grows up along Mississippi River.", 10.99, "tom_sawyer.jpg", 45, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Mark Twain"));
        createBook.add("To the Lighthouse", 1000000306, "Stream-of-consciousness landmark novel about Ramsay family in Scotland.", 12.99, "tothelighthouse.jpg", 28, Book.Status.IN_STOCK, catMap.get("classic"), authorMap.get("Virginia Woolf"));

        // Save all books to Database
        bookDao.saveAll(books);
    }
}