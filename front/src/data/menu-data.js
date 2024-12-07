export const menuItems = [
    { 
        image: require('../assets/menu/socca.png'), 
        title: 'Socca', 
        smallText: 'Originale ou garnies',
        price: '6.50 €',
        preparationTime: '15 min',
        status: 'Disponible',
        fullDescription: 'Socca traditionnelle, faite à base de farine de pois chiches, servie nature ou garnie de légumes frais et fromage.',
        elements: [
            { 
                image: require('../assets/menu/socca.png'), 
                title: 'Socca Nature', 
                smallText: 'Classique', 
                price: '6.50 €', 
                preparationTime: '15 min', 
                status: 'Disponible',
                fullDescription: 'Star incontestée de tous nos plats ! On lui doit la célébrité de Chez Pipo. A base de farine de pois chiche cette galette, sans gluten, croustillante et moelleuse justifie à elle seule votre passage ici. Plus qu’un plat la socca est une véritable tradition et on la déguste entre amis ou en famille, depuis des générations.'
            },
            { 
                image: require('../assets/menu/socca-garnie.png'), 
                title: 'Socca Garnie', 
                smallText: 'Avec fromage ou légumes', 
                price: '8.00 €', 
                preparationTime: '18 min', 
                status: 'Disponible', 
                fullDescription: 'Chez Pipo, nous vous proposons une déclinaison de la socca originale en socca garnie. Nos socca garnies sont des recettes originales, sans gluten, aux saveurs méridionales : cébettes, poivrons marinés, aubergines marinées, tomates confites ou filets d’anchois émincés sont autant d’ingrédients que nous proposons pour découvrir une nouvelle façon de déguster la socca.'
            },
        ],
    },
    { 
        image: require('../assets/menu/pissaladiere.png'), 
        title: 'Entrées', 
        smallText: 'Pissaladière, tartinade, pan bagnat...',
        price: '7.00 €',
        preparationTime: '12 min',
        status: 'Indisponible',
        fullDescription: 'Des entrées savoureuses, typiques de la région, à base de légumes et d\'olives.',
        elements: [
            { 
                image: require('../assets/menu/pissaladiere.png'), 
                title: 'Pissaladière', 
                smallText: 'Spécialité locale', 
                price: '7.00 €', 
                preparationTime: '12 min',
                status: 'Disponible',
                fullDescription: 'Autre plat phare de la cuisine niçoise familiale, la pissaladière est constituée d’une pâte à pain historiquement badigeonnée de pissalât et recouverte d’oignons caramélisés. Le pissalât est une purée de poissons salés, qui a d’ailleurs donné son nom à la spécialité. Cette purée est fabriquée à base d’un mélange pilé de sardines, d’anchois ou de poutine. Aujourd’hui, il est plus souvent remplacé par des filets d’anchois en garniture de la pissaladière. Les oignons, cuits séparément, deviennent presque confits. C’est un produit d’apéritif individuel ou à partager.'
            },
            { 
                image: require('../assets/menu/tartinades.png'), 
                title: 'Tartinade', 
                smallText: 'Assortiment de tartines', 
                price: '5.50 €', 
                preparationTime: '10 min', 
                status: 'Disponible',
                fullDescription: 'Produit d’apéritif idéal pour patienter jusqu’à l’arrivée de votre plat principal. Différentes saveurs locales en duo à tartiner sur des croûtons de pain fraichement doré. Tapenades d’olives, crème d’anchoïade, mélange de légumes provençal ou tomates séchées tout est présent pour vous faire passer un moment d’apéro niçois.'
            },
            { 
                image: require('../assets/menu/pain-bagnat.png'), 
                title: 'Pan Bagnat', 
                smallText: 'Trop bon', 
                price: '6.00 €',
                preparationTime: '15 min',
                status: 'Disponible',
                fullDescription: '« Bagnat » signifie « mouillé » en niçois et cela fait allusion au pain qui doit être mouillé par le jus des tomates et l’huile d’olive. Aujourd’hui, ce plat est confectionné à partir des mêmes ingrédients que la salade niçoise que nous plaçons dans un pain blanc moelleux. Le mini pan bagnat est une portion individuelle de dégustation. Produit idéal pour un petit creux ou en entrée.'
            },
        ],
    },
    { 
        image: require('../assets/menu/biere.png'), 
        title: 'Boissons', 
        smallText: 'Bières, vins, sodas...',
        price: '3.50 €',
        preparationTime: '5 min',
        status: 'Disponible',
        fullDescription: 'Une sélection de boissons rafraîchissantes pour accompagner votre repas.',
        elements: [
            { 
                image: require('../assets/menu/biere.png'), 
                title: 'Bière Blonde', 
                smallText: 'Légère et rafraîchissante', 
                price: '3.50 €', 
                preparationTime: '5 min', 
                status: 'Disponible',
                fullDescription: 'Une bière légère, idéale pour les journées chaudes.'
            },
            { 
                image: require('../assets/menu/biere.png'), 
                title: 'Bière Ambrée', 
                smallText: 'Notes caramélisées', 
                price: '4.00 €', 
                preparationTime: '5 min', 
                status: 'Disponible', 
                fullDescription: 'Une bière avec des notes de caramel et un goût légèrement sucré.'
            },
        ],
    },
    { 
        image: require('../assets/menu/tourte-de-blette.png'), 
        title: 'Desserts', 
        smallText: 'Tourte de blettes, glaces',
        price: '5.00 €',
        preparationTime: '10 min',
        status: 'Disponible',
        fullDescription: 'Desserts maison faits avec des ingrédients frais et locaux.',
        elements: [
            { 
                image: require('../assets/menu/glace.png'), 
                title: 'Glace Artisanale', 
                smallText: 'Vanille, chocolat...', 
                price: '4.50 €', 
                preparationTime: '5 min', 
                status: 'Disponible',
                fullDescription: 'Des glaces crémeuses faites maison, disponibles en plusieurs parfums.'
            },
            { 
                image: require('../assets/menu/tourte-de-blette.png'), 
                title: 'Tourte de Blettes', 
                smallText: 'Un classique sucré', 
                price: '5.00 €', 
                preparationTime: '15 min',
                status: 'Disponible',
                fullDescription: 'Historiquement, il y avait tellement de blettes dans les campagnes niçoises qu’elles sont présentent dans de nombreuses recettes jusqu’à en faire un dessert. Blettes, pommes fondues, pignons de pin, raisins secs marinés en sont les principaux ingrédients dans un mélange de saveurs doux et harmonieux.'
            },
        ],
    },
];
