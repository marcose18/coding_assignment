import requests
from bs4 import BeautifulSoup


def print_ratings_of_movie(url, movie_name):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    movie_list = soup.find_all('div', class_ = 'ratingValue')
    if(len(movie_list) == 0):
        print("Ratings for the movie is not available at IMDB, Needs atleast 5 ratings")
    else :
        print("Ratings of the movie", movie_name, "is:", movie_list[0].strong.span.text)
        print(movie_list[0].strong['title'])

if __name__ == '__main__' :
    base_url = 'https://www.imdb.com'
    url = 'https://www.imdb.com/find?ref_=nv_sr_fn&q=shawshank+redemption&s=all'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    movie_list = soup.find_all('tr', class_ = 'findResult odd')
    movie_list += soup.find_all('tr', class_ = 'findResult even')
    # print(len(movie_list), type(movie_list))
    # print(movie_list)
    for movie in movie_list:
        if(movie.a['href'].startswith('/title')):
            soup = BeautifulSoup(str(movie),"html.parser")
            movie_name = soup.find_all('td')[1]
            # print(movie_name.text) 
            # print(movie.a['href'])
            # print(base_url + movie.a['href'])
            print_ratings_of_movie(base_url + movie.a['href'], movie_name.text)
    # print(len(movie_list), type(response.text), type(str(movie_list[0])), movie_list[0].a['href'])
    # soup = BeautifulSoup(str(movie_list[0]), "html.parser")
    # link = soup.find('a', href = True)
    # print(link['href'], type(link['href']))
